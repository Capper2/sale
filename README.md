# Проверочное задание "Sale"

## Getting Started

### Prerequisites

Для сборки проекта необходимы JDK8:
* [JDK8](http://www.oracle.com/technetwork/java/javase/downloads/2133151)
* [Maven](https://maven.apache.org/)

Maven или Gradle:
* [Maven](https://maven.apache.org/)
* [Gradle](https://gradle.org/install/#manually)

```
java -version
mvn -version
gradle -version
```

### Сбока
Maven

```
mvn clean package
```

Gradle

```
gradle build
```

### Запуск
Maven

```
java -jar sale-generator\target\task1-jar-with-dependencies.jar -o "sale-generator\src\test\resources\offices.txt" -oc 90000 -of operations.csv
java -jar sale-group\target\task2-jar-with-dependencies.jar -gd sums-by-dates.txt -go sums-by-offices.txt -of operations.csv
```

Gradle

```
java -jar sale-generator\build\libs\sale-generator.jar -o "sale-generator\src\test\resources\offices.txt" -oc 90000 -of operations.csv
java -jar sale-group\build\libs\sale-group.jar -gd sums-by-dates.txt -go sums-by-offices.txt -of operations.csv
```

### Настройки

 * настройка логгирования
 
 `-Dlogback.configurationFile="path/to/project/sale-generator/src/main/resources/logback.xml"`

## License

[MIT License](LICENSE)