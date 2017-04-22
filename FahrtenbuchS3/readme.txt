
Dieses Beispielprojekt läßt sich am besten verstehen,
wenn Sie die Dateien in der folgenden Reihenfolge lesen:

1.	WebContent\WEB-INF\web.xml

	web.xml vereinbart Spring als das Servlet, das für die Applikation verwendet wird.
	Ausserdem wird noch das url pattern *.html spezifiziert. Dies bedeutet, dass alle
	Befehle an das System mit .html enden müssen - z.B. http://.../index.html
	
	Unter <contextConfigLocation> wird die Konfigurationsdatei für Spring definiert.

2.	src\application-context.xml

    Der Application-Context beinhaltet die Beschreibung von Java Beans (eine spezielle
    Art von Java Klassen), die für die Anwendung verwendet werden. Da es sich hier um
    das Spring Framework handelt, spricht man bei den Beans, die im Application-Context
    definiert sind, auch von Spring-Beans.
    
    Spring-Beans sind Singletons. Diese Klassen werden beim Start von Spring instanziert
    und die Instanzen bleiben dann im Hauptspeicher, bis Spring wieder beendet wird.
    Die Instanzen werden <property> Spezifikationen mit Informationen versorgt. Spring-
    Beans können mit Hilfe von ids aufeinander verweisen.
    
    application-context.xml ist hier in 5 Abschnitte untergliedert.
    
    a) Die ersten Zeilen legen fest, dass die Controller durch Java Annotationen
       definiert werden. 
       <context:component-scan base-package="de.hdu.pms.ctrl" /> besagt, dass dieses
       Verzeichnis nach Klassen mit Annotationen durchsucht wird. Spring Controller
       sind mit @Controller Annotationen gekennzeichnet, die eine Methode einer Klasse
       als Befehlsempfänger auszeichnen.
       
    b) Die Konfiguration der Datenbank
	   Falls noch keine Datenbank existiert, dann wir eine neue erzeugt.
       
    c) Hibernate (Model) und Data Access Objects (DAO)
       Hibernate wird durch die Angabe der Model Klassen (in de.hdu.pms.model) konfiguriert.
	   Jedes DAO Objekt erhält die hibernateSessionFactory,
	   die dann im der jeweiligen DAO Klasse als 'template' verwendet werden kann.
       
    d) Controllers
       
    e) Views
    
4.	src\pms.controller

	Enthält die Controller der Applikation, die die einzlnen Kommandos verarbeiten.
	Beispiele (die einzelnen Klassen enthalten Kommentare, die die Funktionsweise
	erklären):
	a)	Home.java 
	b)	AlleBoote.Java

5.	src\de.hdu.pms.dao

6.	src\de.hdu.pms.model

7.	WebContent\view

8.	test

	Der Ordner test wurde so kofiguriert, dass die class Dateien auch in diesem Ordner
	abgelegt werden. Dies hat den Vorteil, dass Tests nicht der Web Applikation gespeichert
	werden.
	
	Die Packages in test werden den Packages in src nachgebildet.
	
	Wenn ein Test gestartet wird, dann lädt er eine eigene Spring Konfiguration
	(in test/test-application-context.xml), die sich von der Spring Konfiguration der
	Tomcat Applikation darin unterscheidet, dass eine andere Datenbank verwendet wird.

9.	UTF-8 Zeichenkodierung

	Die korrekte Verwendung von UTF-8 ist nicht enfach. Man muss darauf achten, dass die gesamte
	Kette von Programmen und Datenübertragungen korrekt UTF-8 unterstützt. Folgende Dinge müssen bedacht
	werden:

	 - Schreiben Sie UTF-8 immer groß (und nicht utf-8, der IE versteht das nicht)
	 - Eclipse auf UTF-8 stellen
	 - Browser auf UTF-8 stellen
	 - Die Datenbank muss mit UFT-8 Kodierung erzeugt werden.
	 - Benutzen Sie sowohl
	   <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	   als auch
	   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	   in Ihren jsp Dateien.
	   In den jsp Datein müssen Sie auch
	   httpServletRequest.setCharacterEncoding("UTF-8");
	   setzen, bevor Sie die Parameter aus dem Request auslesen.
	 - Senden Sie Formularinhalte per post (sonst müssen Sie noch eine URL Kodierung einführen)

Links:

http://www.springbyexample.org/examples/simple-spring-mvc-form-annotation-config-webapp.html

http://static.springsource.org/spring/docs/3.0.0.M3/spring-framework-reference/html/ch16s11.html