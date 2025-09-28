## 1. Problem Statement
Write a program (only in C# or Java or C), to explore a 5x5 array for a treasure. The values in the array are clues. Each cell contains an integer between 11 and 55; for each value the ten's digit represents the row number and the unit's digit represents the column number of the cell containing the next clue.

Consider that first clue is always 11. So starting in the upper left corner (at 1,1), use the clues to guide your search of the array. (So, in the given example, the first three clues are 11, 34, 42). The treasure is a cell whose value is the same as its coordinates.

Your program must first read in the treasure map data from the standard input (console), into a 5 by 5 array.

Your program should output the cells it visits during its search, and a message indicating where you found the treasure.

Optional Requirement (carries additional points, if implemented): While your program is searching for the treasure, it should detect infinite loops, and stop-and-report as soon as it detects one.

Object Oriented solutions will be preferred.

The syntax of creating a 2-D array in C# is:
```
int [,] array = new int[5, 5];
```

### Sample Input 1
```
34,21,32,41,25
14,42,43,14,31
54,45,52,42,23
33,15,51,31,35
21,52,33,13,23
```

### Sample Output 1
```
11 -> 34 -> 42 -> 15 -> 25 -> 31 -> 54 -> 13 -> 32 -> 45 -> 35 -> 23 -> 43 -> 51 -> 21 -> 14 -> 41 -> 33 -> 52 -> Treasure Found
```

### Sample Input 2
```
34,21,32,41,25
14,42,43,14,42
54,45,52,42,23
33,15,51,31,35
21,52,33,13,23
```

### Sample Output 2
```
11 -> 34 -> 42 -> 15 -> 25 -> Loop Found
```

Note:
Values are comma separated.

---

## 2. Stone Processing

You are working on a project to process stones in a stone quarry using Java. Each stone has different characteristics, such as weight (in kilograms) and color. Your task is to write a Java program that uses the Stream API to perform various operations on a collection of stones.
Note that your code should match the specifications in a precise manner. Consider default visibility of classes, data fields, and methods unless mentioned otherwise.

Specifications:
```
class definitions:
class Stone:
data member:
double weight
String color visibility: private
Stone(double weight, String color) : Define the constructor with
public visibility
Define getters for all the data members with public visibility.
toString method has been defined for you as a part of the code
stub.

class StoneProcessingApp:
method definitions:
getTotalWeight(List<Stone> stones) :
return type: double visibility: public

getRedStone(List<Stone> stones) :
return type: List<Stone>
visibility: public

getHeaviestStone(List<Stone> stones) :
return type: Stone visibility: public
```

Task:
class Stone:
- Define the class according to the above specifications 

class StoneProcessingApp:
Implement the below method for this class using Stream API methods:
- **double getTotalWeight(List<Stone> stones):** Calculate and return the total weight of all stones.
- **List<Stone> getRedStone(List<Stone> stones):** Find and return all stones that are of a specific color ("red").
- **Stone getHeaviestStone(List<Stone> stones):** Find and return the heaviest stone in terms of weight.

Sample Input:
```
List<Stone> stones = new ArrayList<>();

stones.add(new Stone(5.0, "red"));
stones.add(new Stone(8.0,"blue"));
stones.add(new Stone(6.5, "red"));
stones.add(new Stone(4.2, "green"));
stones.add(new Stone(7.8, "blue"));

StoneProcessingApp sta = new StoneProcessingApp();
sta.getTotalWeight(stones);
sta.getRedStone(stones);
sta.getHeaviestStone(stones);
```

Sample Output:
```
31.5
[Stone{weight=5.0, color='red'}, Stone{weight=6.5, color='red'}]
Stone{weight=8.0, color='blue'}
```

### 3. Apparel Inventory

You are tasked with managing an apparel inventory system using Java's Stream API. The apparel inventory is represented as a list of Apparel objects.
Implement a Java code based on the following specifications. Note that your code should match the specifications in a precise manner. Consider default visibility of classes, data fields, and methods unless mentioned otherwise.

### 🧩 Class Definitions & Requirements

#### ✅ `Apparel` Class

Define a class `Apparel` with the following private data members:

* `String id`
* `String name`
* `String category`
* `String size`
* `String color`
* `double price`

##### Constructor:

* `public Apparel(String id, String name, String category, String size, String color, double price)`

##### Getters (with `public` visibility):

* `getId()`
* `getCategory()`
* `getPrice()`
* `getColor()`

##### Other Notes:

* A `toString()` method has already been implemented and will be provided as part of the stub.


#### ✅ `ApparelInventory` Class

Implement the following methods using **Stream API**:

1. `public long getCountOfApparels(List<Apparel> inventory)`
   → Returns the total number of apparel items in the inventory.

2. `public List<String> getApparelCategory(List<Apparel> inventory)`
   → Returns a list of all **unique** apparel categories present in the inventory.

3. `public double getValueOfInventory(List<Apparel> inventory)`
   → Returns the **total value** of all apparel items (sum of their prices).


### Sample Input

```java
List<Apparel> inventory = new ArrayList<>();
inventory.add(new Apparel("A001", "Blue Shirt", "Shirts", "M", "Blue", 45.99));
inventory.add(new Apparel("A002", "Red Dress", "Dresses", "S", "Red", 65.99));
inventory.add(new Apparel("A003", "Black Pants", "Pants", "L", "Black", 39.99));
inventory.add(new Apparel("A004", "Green Scarf", "Accessories", "N/A", "Green", 19.99));
inventory.add(new Apparel("A005", "White Shirt", "Shirts", "L", "White", 55.99));
```

### Sample Usage

```java
ApparelInventory inventoryManager = new ApparelInventory();

System.out.println(inventoryManager.getCountOfApparels(inventory));
// Output: 5

System.out.println(inventoryManager.getApparelCategory(inventory));
// Output: [Shirts, Dresses, Pants, Accessories]

System.out.println(inventoryManager.getValueOfInventory(inventory));
// Output: 227.95
```
