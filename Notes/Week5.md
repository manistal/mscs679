# Futures and Promises

## Actors

Strengths:
    Supported by Mathematically theory 
    Shared nothing, unlike threads (limits hazards)
    Support scale-up and scale-out

Weaknesses:
    High Latency 
    Nonfunctional (Heavily OOP) 
    Complex to set up 

## Futures

Invented in the 70s and 80s 

Exist in many different languages (C++20!)

Definition: Proxy for Result initially Unknown 

Proxy starts immediately upon creation 

Replaces the concept of Actors in Scala 

## Futures and Promises

These two concepts go hand-in-hand

Promise: Defines a result (type) -- settor
Future: Fufills a promise at a future time -- gettor

Single Promise could be satisfied by different possible Futures

## Await Class

Will be using this extensively in this course

Specifically Designed for 3 things:
    1) Implicitly creates a Promise 
        From the Future, you can infer the Promise
    2) Synchronizes Future Completion
        (i.e. like Join() on a child thread)
    3) Unwraps the Future and gets the result out

## Scala Runtime Scheduler

Scala runtime manages the scheduler of the Future Pool 

We can heklp the scheduler by doing one of two things by limiting the Future creation:
    1) Algorithmically (break things up based on problem size)
    2) Limit to the number of cores (break things up based on cores)

## If we wait for futures out of order...

Doesn't matter, the long pole is the long pole

All Futures are Running Concurrently, even if we don't Await concurrently 


## How can we go faster?

Scale Out now

Futures don't cross Hosts, what does?

Use the Dispatcher-Worker Model

Dispatcher Actor calculates Partitions

Transmits partitions to workers 

Worker sub-partitions and uses Futures 

## Last Actors 

Contains Dispatcher-Worker Pattern for Distributed Actors

It does not know about perfect numbers
    Extend Dispatcher and Worker into specialized classes for Perfect numbers


## Summary

- A Promise Defines a Type
- A Future Delivers a Type
- Await implicitly creates a Promise given a Future
- Await waits for Future Arrival 
- Await unwraps the Future type
- Future PNF Partitions number range -- One Future / partition
- Each Future in PNF finds only partial SOF for its partition
- Await in Future PNF sums the partial sums 

