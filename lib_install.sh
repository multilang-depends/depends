mvn install:install-file -DgroupId=org.jruby	-DartifactId=jrubyparser  -Dversion=0.5.5-SNAPSHOT    -Dpackaging=jar -Dfile=./jars/jrubyparser-0.5.5-SNAPSHOT.jar  -DgeneratePom=true

mvn install:install-file -DgroupId=org.eclipse.cdt	-DartifactId=org.eclipse.cdt.core  -Dversion=6.9.0.201909091953    -Dpackaging=jar -Dfile=./jars/org.eclipse.cdt.core_6.9.0.201909091953.jar -DgeneratePom=true

mvn install:install-file -DgroupId=eclipse-photon -DartifactId=org.eclipse.equinox.registry -Dversion=3.8.500.v20190714-1850  -Dpackaging=jar -Dfile=./jars/org.eclipse.equinox.registry_3.8.500.v20190714-1850.jar -DgeneratePom=true

mvn install:install-file -DgroupId=eclipse-photon -DartifactId=org.eclipse.core.runtime -Dversion=3.14.0.v20180417-0825  -Dpackaging=jar -Dfile=./jars/org.eclipse.core.runtime-3.14.0.v20180417-0825.jar  -DgeneratePom=true

mvn install:install-file -DgroupId=eclipse-photon -DartifactId=org.eclipse.equinox.common -Dversion=3.10.500.v20190815-1535 -Dpackaging=jar -Dfile=./jars/org.eclipse.equinox.common_3.10.500.v20190815-1535.jar -DgeneratePom=true

mvn install:install-file -DgroupId=eclipse-photon -DartifactId=org.eclipse.osgi -Dversion=3.15.0.v20190830-1434 -Dpackaging=jar -Dfile=./jars/org.eclipse.osgi_3.15.0.v20190830-1434.jar -DgeneratePom=true

mvn install:install-file -DgroupId=eclipse-photon -DartifactId=org.eclipse.equinox.preferences -Dversion=3.7.500.v20190815-1535 -Dpackaging=jar -Dfile=./jars/org.eclipse.equinox.preferences_3.7.500.v20190815-1535.jar -DgeneratePom=true

mvn install:install-file -DgroupId=eclipse-photon -DartifactId=org.eclipse.core.jobs -Dversion=3.10.500.v20190620-1426 -Dpackaging=jar -Dfile=./jars/org.eclipse.core.jobs_3.10.500.v20190620-1426.jar -DgeneratePom=true

mvn install:install-file -DgroupId=eclipse-photon -DartifactId=org.eclipse.core.resources -Dversion=3.13.500.v20190819-0800 -Dpackaging=jar -Dfile=./jars/org.eclipse.core.resources_3.13.500.v20190819-0800.jar -DgeneratePom=true

