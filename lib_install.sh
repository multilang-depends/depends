mvn install:install-file -DgroupId=org.eclipse.cdt	-DartifactId=org.eclipse.cdt.core  -Dversion=6.5.0.201806170908    -Dpackaging=jar -Dfile=./jars/org.eclipse.cdt.core-6.5.0.201806170908.jar -DgeneratePom=true

mvn install:install-file -DgroupId=eclipse-photon -DartifactId=org.eclipse.equinox.registry -Dversion=3.8.0.v20180426-1327  -Dpackaging=jar -Dfile=./jars/org.eclipse.equinox.registry-3.8.0.v20180426-1327.jar -DgeneratePom=true

mvn install:install-file -DgroupId=eclipse-photon -DartifactId=org.eclipse.core.runtime -Dversion=3.14.0.v20180417-0825  -Dpackaging=jar -Dfile=./jars/org.eclipse.core.runtime-3.14.0.v20180417-0825.jar  -DgeneratePom=true

mvn install:install-file -DgroupId=eclipse-photon -DartifactId=org.eclipse.equinox.common -Dversion=3.10.0.v20180412-1130 -Dpackaging=jar -Dfile=./jars/org.eclipse.equinox.common-3.10.0.v20180412-1130.jar -DgeneratePom=true

mvn install:install-file -DgroupId=eclipse-photon -DartifactId=org.eclipse.osgi -Dversion=3.13.0.v20180409-1500 -Dpackaging=jar -Dfile=./jars/org.eclipse.osgi-3.13.0.v20180409-1500.jar -DgeneratePom=true

mvn install:install-file -DgroupId=eclipse-photon -DartifactId=org.eclipse.equinox.preferences -Dversion=3.7.100.v20180510-1129 -Dpackaging=jar -Dfile=./jars/org.eclipse.equinox.preferences-3.7.100.v20180510-1129.jar -DgeneratePom=true

mvn install:install-file -DgroupId=eclipse-photon -DartifactId=org.eclipse.core.jobs -Dversion=3.10.0.v20180427-1454 -Dpackaging=jar -Dfile=./jars/org.eclipse.core.jobs-3.10.0.v20180427-1454.jar -DgeneratePom=true
