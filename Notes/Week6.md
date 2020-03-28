# Paralell Collections

## Futures

Strengths:
    Scale-up 
    Portable Concept
    Functional Programming

Weaknesses:
    Scale-out
    Requires synchronization 
    Not widely known 

## Data Parallelism

Think SIMD

Same operations performed on Subsets of Data

Synchronous Computation 

Never has to wait, join, etc

No inherent concurrency hazards (data is immutable and seperate)

Nothing to block -- Operations execute in lockstep

Doesn't care about sequence of operation

## Map Higher Order Function *Function*

Operates in parallel Across Dataset 

Must not require synchronization while operating on the data 

Assumes:
    1) Doesn't introduce var's outside scope
    2) Uses only functions with no side effects
    3) Avoid ordering dependencies 

## Parallel Collections

Complements Standard Collections

Modelled on Data Parallelism 

Backed by Threads 

Requires traversable data structure

Applies a function across the container in parallel 

## .par -- "Free Lunch"?

Creates a new collection and returns a copy

Behaves the same way as a sequential collection, except its backed in threads

Makes your functional code operate in parallel even if you write it as if it's in serial! 

## Parallel Fold

You mostly use `foldLeft()` 

fold -- Doesn't specify direction, lets you run in parallel since sum is transitive

## .par Upsides

1) Functionally equivalent to serial algorithm

2) Need .par only once

3) Easy to verify

4) Easy to debug 

## .par Downsides

1) Copy takes space and time
    Not appropriate for large collections
    Can mitigate by starting with a paralell collection 

2) unclear when you have a parallel collection
    It's a very implicit operation

3) No order dependency allowed in Function

## Traversable Parallel Collections

- Sequence
- Set 
- Map 

## When to use .par

You have a :

1) Parallelizable problem 
2) Long running problem 
3) Medium size data 
    a) depends on machine capacity 
    b) 100s to 1000s on low end machines
    #UseFrickingCUDA

## Summary 

- Standard collection methods are serialized
- Parallel collection methods are backed in threads
- .par creates a parallel collection as a copy
- Seq vs Parallel algorithm distinction not always clear 
- Parallel collections better used with medium sized collection (this is an unclear bullshit statement)




