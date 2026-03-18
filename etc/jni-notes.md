# JNI Notes

## `System.load(String)` vs. `System.loadLibrary`

**Courtesy of Google on 2026-03-18: "Java 21 system.loadLibrary(String) vs system.load(String) difference"**

The core difference is that `System.load(String filename)` requires a full,
absolute path to the native library file, including its platform-specific
extension, while `System.loadLibrary(String libname)` takes a library name and
relies on the JVM to find the library in the system's configured library paths.

* `System.load(String filename)`
  * Argument: Expects the complete, absolute file path to the native library (e.g., "`C:\\libs\\mylib.dll`" on Windows or "`/usr/local/lib/libmylib.so`" on Linux).
  * Mechanism: Loads the specified file directly from the given location.
  * Portability: Less portable because the path is hardcoded and platform-specific path separators and file extensions are required.
  * Dependency Handling: If the loaded library has its own dependencies on other native libraries, those dependencies must be locatable by the operating system's loader (usually through the OS's environment variables like `PATH` on Windows or `LD_LIBRARY_PATH` on Unix/Linux), otherwise a `UnsatisfiedLinkError` may be thrown. 

* `System.loadLibrary(String libname)`
  * Argument: Expects a platform-independent library name (e.g., "`mylib`").
  * Mechanism: The JVM and the underlying operating system work together to convert the library name into a platform-specific filename (e.g., `libmylib.so` on Linux, `mylib.dll` on Windows) and then search for that file in a predefined set of directories specified by the Java system property `java.library.path` (among other potential OS-specific paths).
  * Portability: More portable because the code uses a generic name, and the JVM handles platform-specific naming conventions and search paths.
  * Dependency Handling: This method is generally better designed for managing dependencies between native libraries as the search mechanism is more robust in locating dependent libraries if they are within the search paths. 
  * In summary, `System.loadLibrary` is generally the preferred method for loading native libraries in a portable and standard way, leveraging the JVM's library search mechanisms. `System.load` offers direct control over the specific file path when needed, but at the cost of portability.
