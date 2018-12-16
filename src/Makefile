JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES : \
        Hospital.java \
        Paciente.java \
        Requerimientos.java \
        Main.java

default: classes

classes: $(CLASSES:.java=.class)

clean: $(RM) *.class