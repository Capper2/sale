# Проверочное задание "Sale"

## Getting Started

### Prerequisites

Для сборки проекта необходимы JDK8 и Maven:
* [JDK8](http://www.oracle.com/technetwork/java/javase/downloads/2133151)
* [Maven](https://maven.apache.org/)


```
java -version
mvn -version
```

### Сбока

```
mvn clean package
```


### Запуск

```
java -jar sale-generator\target\task1-jar-with-dependencies.jar -o "sale-generator\src\test\resources\offices.txt" -oc 90000 -of operations.csv
java -jar sale-group\target\task2-jar-with-dependencies.jar -gd sums-by-dates.txt -go sums-by-offices.txt -of operations.csv
```

### Настройки

 * настройка логгирования
 
 `-Dlogback.configurationFile="path/to/project/src/main/resources/logback.xml"`

## License

[MIT License](LICENSE)