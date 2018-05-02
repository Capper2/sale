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

### Запуск

```
java -jar target\sale-jar-with-dependencies.jar -o "src\test\resources\offices.txt" -oc 90000 -of result.csv
```

### Настройки

 * настройка логгирования
 
 `-Dlogback.configurationFile="path/to/project/src/main/resources/logback.xml"`

## License

[MIT License](LICENSE)