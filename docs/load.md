
[back to readme](../../../)

# Data.load

## Description

Data.load sets the path of the OSHDB file.

## Usage

```java
Data.load(file);
```

## Arguments

file			path to the OSHDB file.

## Examples

```java
Data.load("C:/path/to/your/OSMdata/baden-wuerttemberg.oshdb.mv.db"); // loading the OSHDB file from its directory for an export to CSV or JSON
Export.csv(new StrategyBuildings(), Cols.BASIC_INFORMATION); 
```