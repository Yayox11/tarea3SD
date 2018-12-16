JFLAGS = -g
JC = javac -sourcepath / -classpath gson/gson-2.6.2.jar
SOURCES = Requerimientos.java Paciente.java Hospital.java HacerRequerimientos.java HandleClient.java Multiserver.java Multiserver2.java
.SUFFIXES: .java .class
.java.class: ;$(JC) $(JFLAGS) $*.java
LIBS = gson/gson-2.6.2.jar


CLASSES = \
    HacerRequerimientos.java \
    Requerimientos.java \
    Paciente.java \
    Hospital.java \
    HandlerClient.java \
    Multiserver.java \
    Multiserver2.java

default: classes

classes: $(CLASSES:.java=.class)

clean: $(RM) *.class