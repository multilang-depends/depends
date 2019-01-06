rm *.java
rm *.class

export CLASSPATH=.:/usr/share/java/stringtemplate4.jar:/usr/share/java/antlr4.jar:/usr/share/java/antlr4-runtime.jar:/usr/share/java/antlr3-runtime.jar/:/usr/share/java/treelayout.jar

antlr4 RubyLexer.g4
antlr4 RubyParser.g4

javac -cp $CLASSPATH *.java

exec java -cp $CLASSPATH org.antlr.v4.gui.TestRig Ruby prog -tokens $1
