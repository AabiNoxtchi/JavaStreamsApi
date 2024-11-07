# Introduction

## Java Streams Api ##


This project aims to demonstrate some usages of Java Streams Api.

Java Streams Api is a powerful Api, that can be used in various vast situations, calculations, transformations, filtering, 
and many more.

In this project very simple class models are used, which are not advisable to use in real world models, such models should have ids,
and available quantities would have their own classes, to highlight some of the issues, however these models are designed in such way
just to fulfil the aim of current task - to use Java Streams Api -

To simplify collecting and printing data, printing is delegated to separate Printer classes, which task is just to print the data,
and to collect the data in an easy and convenient two collector methods to maps are in
    - BookService.getBooksStats
    - StatsService.getStats

To provide initial data there are two methods - BookService.initBooks  - SaleService.initSales

some of demonstrated usages of Streams :
- map
- flatMap
- filter
- sum
- count
- max
- min
- reduce
- sorted
- Collectors.
    - partitioningBy
    - mapping    
    - summingInt
    - summingDouble
    - counting
    - groupingBy
    - collectingAndThen
    - toMap
