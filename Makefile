MVn=mvn
SCREEN=flopbox
ARGS=-DskipTests=true
JARNAME=flopbox-1-jar-with-dependencies.jar
JAVA=java -jar target/

all: compile jar

clean:
	$(MVn) clean

compile:
	$(MVn) package $(ARGS)

jar:
	screen -S $(SCREEN) -dm bash -c "$(JAVA)$(JARNAME)"

see:
	screen -r $(SCREEN)