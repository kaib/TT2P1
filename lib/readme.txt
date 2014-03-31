GigaSpaces XAP includes the following 3 categories of jars files: 

* Required jars: 
- Contains mandatory jars for runtime and compile time
- Located under lib/required
- You should include all the jars in this folder in your classpath when compiling GigaSpaces applications
- For standalone applications which do not run inside the GigaSpaces runtime environment, you should also 
  include them in your runtime classpath

* Optional jars 
- Required for runtime only when using the respective optional components
- Located under lib/optional and contains the below optional components
- lib/optional/spring includes:
  - com.springsource.net.sf.cglib which is required at runtime when proxying full target classes via Spring AOP
  - com.springsource.javax.annotation, which contains the JSR-250 common annotations
    (http://jcp.org/en/jsr/detail?id=250) and is required at runtime when using Spring's common 
    annotations support on JDK < 1.6
- lib/optional/openspaces includes optional OpenSpaces modules and other OpenSpaces products. 
  Includes the following: 
  - mule-os.jar which contains the GigaSpaces-Mule integration package
  - The schema directory which includes the XSD files for all OpenSpaces components
  - gs-openspaces-src.zip which contains the sources of the OpenSpaces framework
- lib/optional/pu-common directory: placing jars in this directory means they will be loaded by each processing
   unit instance its own separate classloader (called the service classloader, see the Class Loaders section
   below). You can either place these jars in each GigaSpaces installation in your network, or point the
   pu-common directory to a shared location on your network by specifying this location in the com.gs.pu-common
   system property in each of the GSCs on your network.

* Platform jars
- Located under lib/platform
- Contains jars which are required by the GigaSpaces runtime environment and tooling. 
  In most cases you will not need to include any of the jars in this directory in your compile time
  or runtime classpath.
