/*
 * Copyright 2011 Tsutomu YANO.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.ac.chitose.service.Class;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.wicket.Component;
import org.apache.wicket.IEventDispatcher;
import org.apache.wicket.event.IEvent;

/**
 * <p>
 * アノテーション {@link EventHandler} を付与したメソッドをイベントハンドラであると
 * し、イベントペイロードがイベントハンドラの引数の型と一致する場合には、該当メソッドを コールする、{@code IEventDispatcher}
 * 実装です。
 * <p>
 * イベントハンドラとなるメソッドは、必ず、引数がひとつでなければなりません。引数の型は 対応するイベントのペイロードと同じクラスでなければなりません。
 * <p>
 * イベントハンドラはprivateであっても構いません。このディスパッチャはリフレクションを 使ってメソッドを呼び出します。
 * <p>
 * このクラスはWicketのあらゆるイベント送信ごとに呼び出されることに注意してください。
 * ディスパッチャの呼び出しにより重い処理が行われると、アプリケーションのパフォーマンスが 低下します。<br>
 * そのため、このクラスでは、一度走査したクラスについては、見つかったイベントハンドラの メソッド情報を、{@link ClassDescriptor}
 * をキーに、{@code classMap}フィールドに格納します。 一度クラスのパースが行われると、それ以降は、{@code classMap}
 * に格納した情報を利用します。 これにより、ディスパッチ処理の不可を軽減しています。
 * <p>
 * {@link ClassDescriptor}と{@link MethodDescriptor}は、クラスそのものやメソッドそのものを
 * 格納することはない点に注意してください。クラスやメソッドをマップに格納すると、強参照となる ため、クラスのアンロードができなくなってしまいます。
 * {@link ClassDescriptor}や{@link MethodDescriptor} は、文字列で情報を格納します。
 * {@link ClassDescriptor#toClass()}や{@link MethodDescriptor#toMethod()}
 * を使うことで、元のクラスやメソッドを復元することが出来ます。
 * <p>
 * {@link ClassDescriptor}はクラスローダについての情報は記録しません。いくつかの
 * IoCコンテナは開発時にクラスローダの差し替えを行うため、クラスの比較にクラスローダを含めることは できません。常に
 * {@link Thread#getContextClassLoader()}の返すクラスローダを利用します。
 * 
 * @author Tsutomu YANO
 */
public class AnnotationEventDispatcher implements IEventDispatcher {
	private final Map<ClassDescriptor, Set<MethodDescriptor>> classMap = new HashMap<>();

	private void invokeHandler(Method method, Object sink, Object payload) {
		try {
			method.setAccessible(true);
			method.invoke(sink, payload);
		} catch (IllegalAccessException ex) {
			throw new IllegalStateException("Could not access to the method. EventHandler must be a public method.",
					ex);
		} catch (InvocationTargetException ex) {
			throw new RuntimeException("underlying method thrown a exception. see the stack trace.", ex);
		}
	}

	@Override
	public void dispatchEvent(Object sink, IEvent<?> event, Component component) {
		Class<?> sinkClass = sink.getClass();
		Object payload = event.getPayload();
		Class<?> payloadClass = payload.getClass();

		if (payload != null) {
			Set<MethodDescriptor> descriptors = new HashSet<>();
			Class<?> clazz = sinkClass;
			ClassDescriptor classDescriptor = new ClassDescriptor(clazz);
			Set<MethodDescriptor> cachedMethods = classMap.get(classDescriptor);

			if (cachedMethods == null) {
				Set<MethodDescriptor> methods = new HashSet<>();
				classMap.put(classDescriptor, methods);

				while (clazz != null && !clazz.equals(Object.class)) {
					for (Method method : clazz.getDeclaredMethods()) {
						method.setAccessible(true);
						MethodDescriptor methodDescriptor = new MethodDescriptor(clazz, method);

						// 一度処理したメソッドと同じシグニチャのメソッドはオーバーライドされており、
						// 先にサブクラスのほうで処理済みなので、処理しない
						if (!descriptors.contains(methodDescriptor)) {
							descriptors.add(methodDescriptor);
							if (method.isAnnotationPresent(EventHandler.class)) {
								methods.add(methodDescriptor);
								Class<?>[] paramTypes = method.getParameterTypes();
								if (paramTypes.length == 1 && paramTypes[0].isAssignableFrom(payloadClass)) {
									invokeHandler(method, sink, payload);
								}
							}
						}
					}

					clazz = clazz.getSuperclass();
				}
			} else {
				for (MethodDescriptor methodDescriptor : cachedMethods) {
					try {
						Method method = methodDescriptor.toMethod();
						Class<?>[] paramTypes = method.getParameterTypes();
						if (paramTypes.length == 1 && paramTypes[0].isAssignableFrom(payloadClass)) {
							invokeHandler(method, sink, payload);
						}
					} catch (ReflectiveOperationException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
	}

	public static class ClassDescriptor {
		private String className;

		public ClassDescriptor(Class<?> clazz) {
			this.className = clazz.getName();
		}

		public Class<?> toClass() {
			ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
			try {
				return contextClassLoader.loadClass(this.className);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (this == obj) {
				return true;
			}

			if (obj.getClass().equals(ClassDescriptor.class) == false) {
				return false;
			}
			ClassDescriptor desc = (ClassDescriptor) obj;
			return className.equals(desc.className);
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(className);
		}

		@Override
		public String toString() {
			return new ToStringBuilder(this).append("className", className).toString();
		}
	}

	public static class MethodDescriptor {
		private ClassDescriptor classDescriptor;
		private String methodName;
		private List<String> argTypes = new ArrayList<>();

		public MethodDescriptor(ClassDescriptor classDescriptor, String methodName, List<String> argTypes) {
			super();
			this.classDescriptor = classDescriptor;
			this.methodName = methodName;
			this.argTypes = argTypes;
		}

		public MethodDescriptor(Class<?> clazz, Method method) {
			super();
			this.classDescriptor = new ClassDescriptor(clazz);
			this.methodName = method.getName();
			Class<?>[] parameterTypes = method.getParameterTypes();
			if (parameterTypes != null) {
				for (Class<?> type : parameterTypes) {
					argTypes.add(type.getName());
				}
			}
		}

		public ClassDescriptor getClassDescriptor() {
			return classDescriptor;
		}

		public String getMethodName() {
			return methodName;
		}

		public List<String> getArgTypes() {
			return new ArrayList<>(argTypes);
		}

		public Method toMethod() throws NoSuchMethodException, SecurityException, ClassNotFoundException {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			List<Class<?>> classList = new ArrayList<>();
			for (String argType : getArgTypes()) {
				Class<?> loadClass = classLoader.loadClass(argType);
				classList.add(loadClass);
			}
			Class<?> targetClass = getClassDescriptor().toClass();
			return targetClass.getDeclaredMethod(getMethodName(), classList.toArray(new Class<?>[0]));
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (this == obj) {
				return true;
			}

			if (obj.getClass().equals(MethodDescriptor.class) == false) {
				return false;
			}

			MethodDescriptor desc = (MethodDescriptor) obj;
			boolean equals = new EqualsBuilder().append(classDescriptor, desc.classDescriptor)
					.append(methodName, desc.methodName).append(argTypes, desc.argTypes).isEquals();
			return equals;
		}

		@Override
		public int hashCode() {
			return Objects.hash(classDescriptor, methodName, argTypes);
		}

		@Override
		public String toString() {
			return new ToStringBuilder(this).append("methodName", methodName).append("argTypes", argTypes).toString();
		}
	}
}
