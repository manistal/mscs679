# Intro to Scala

Mostly uninteresting, just look things up as needed


## Val vs Var

Var is mutable

Val is immutable

Immutability is important because it because we don't need to deal with locking, timeouts or hazards 

## Case Classes

Case Classes are like regular classes with a few key differences
    - Good for modeling immutable data 
    - Useful in pattern matching

Does not require the `new` keyword to instantiate 
    - This is because case classes have an apply method by default which takes care of object construction 
    - When you create a case class with parameters, the parameters are public val's (like a Struct)

Case classes are compared by content, not by reference

Can be used in Match statements based on abstract base classes 

# Free Lunch

## What is the "Free Lunch"?

Processors have improved straight line execution flow for 30+ years
    - Clock Speed
    - Execution Optimization  
    - Cache 

All these improvements are concurrency agnostic, they make _all_ software faster 

The only people who had to pay attention were designing compilers

## Hardware Limitations

- Transitors continue to double in amount
- However, clock speed and perf/clock have stagnated 
    - There are physical switching limitations! (thanks physics!)

So what do we do with the extra transitors? We PUT MORE CORES! But what does that mean for software? Parallelism

Concurrency is the next major revolution in software development on the same order as OO 

## Benefits and Costs of Concurrency 

Concurrency is hard - the programming model is much harder than with sequential control flow 

If done poorly: Race conditions, hazards, stack corruption, etc 

## What it means for us?

1. The clear primary consequence we've already covers is that applications will need to be increasingly concurrency 

2. Applications are likely to become increasingly CPU bound

3. Efficiency and Performance Optimization will get more important 

4. Programmable languages and systems will be forced to deal with concurrency 



