# Threads

Large diverse topic
    Hardware design to web design 

Different implementations 

## Thread Definition

Single Stream of Execution 
    Hyperthreads => Managed by the Hardware/OS

    Green Threads => Managed by the JVM 
        Basis of all JVM Parallelism 
        Used by the 4 models of parallelism (Actors, futures, parallel collections, and Threads)

## Threads Pros/Cons

Upsides
    - Very easy to start: can scale UP
    - Portable (wherever there's a JVM)

Downsides
    - Depedent on Shared Memory 
    - Can't cross JVM boundaries: Can't scale OUT

## Communication

Threads must share info eventually 

App not done until all its threads finish

Leads to need for Synchronization 

Synchronization and Communication are interchangable concepts

## Synchronization Methods

- Semaphores
    - Provided natively by the JVM 
    - Efficient, but unstructured

- Polling
    - Simple, yet inefficient

- Message passing
    - Elegant, but costly with high communication latency

- Callbacks
    - Cheap, but can be difficult to use 

## Semaphores

Resources that require _mutually exclusive accesss_ are called a _critical region_ 

Monitors are coordinated and spatially distributed (in different regions of the code)

Failure could result in 
    - Race condition (resource is used by BOTH)
    - Deadlock (resource is used by no one)
    - Starvation (inefficient utilization)


## Synchronization Hazards

Not enough synchronization => Race condition

Too much synchronization => Deadlock 

## Definition and Symptoms

Race Condition
    - Correctness of the program depends on the relative speeds of the threads
    - Symptom : Seemingly random results

Deadlock
    - Thread needs a resource it cant get 
    - Symptom: Hang 

## Thread Class

Imported directly from the Java Language

1) Extend Thread, override Run 

2) Create an instance T

3) new Thread(t).start
    Forks control to run method

4) Stops when run reaches end of scope or returns 


## Runnable Interface

Works the same way :

1) Extend Runnable

2) Override run() 

## Synchronized Methods

Function like a tunnel for cars

Exactly one thread allowed to execute inside

All other threads have to block 

Unspecified Thread allowed in provided
    1) One inside exits
    2) One inside waits

## Thread Life Cycle

New => Runnable ==> (Non-Runnable (blocked) <==> Running) ==> Terminated 

## Sleep

Form of synchronization

Puts the thread to sleep for some period of time (Blocked)

Can be invoked anywhere

## Join 

Form of synchronization 

Member of Thread, blocks or waits for another thread to terminate

join() -- Waits indefinitely
join(Timeout) -- waits for Timeout milliseconds

## Wait 

Waits for notify call from within a synchronized block of code 

## Notify

Wakes up a thread that's waiting 

## Thread Saftey

Method Functions correctly for >= 1 Threads concurrently

Two ways to guaruntee:
    1. Synchronize Shared Data Structures
        - Can cause deadlocks
        - Needs to be coded right
    2. Share Nothing
        - This is why functional programming is so powerful
        - Use immutable Data Structures 
        - Make copies 

## Extend Thread or Runnable?

Extend Runnable - It doesn't use Single Inheritance 


## Consumer Producer 

Classic Concurrency Problem where threads are used and needed

Universal in consumerist economy, ubiquitous in process engineering 

Producer Makes Items -> Consumer Consumes them 
    Producer and Consumer work in parallel, but at different speeds!


## Dispatcher Worker Design Pattern

Simple yet powerful way to solve the Consumer Producer problem

Inherently Paralellizable 

1 Dispatcher, N Workers
    1) Dispatcher organizes some tasks and sends to workers
    2) A buffer *Work Queue* between Dispatcher and Workers
    3) Worker does task in order they are received and waits if its queue is empty 
    4) Worker sends results to result queue
    5) Dispatcher waits for all workers to finish

## Why Queues

Flow Control! 

If Dispatcher is faster than Worker, queue is a buffer but queue must be Thread Safe

Could use ListBuffer, but not thread safe

Instead use synchronized ListBuffer aka Mailbox

## Dispatcher Design 

Dispatching could become a bottleneck

Tasks handed out are merely References, not the work themselves (so they don't contain lots of data )

Want to avoid large data transfers, that increase the fraction of serial time, increased latency, Amdahl's law 

## ParaScale 

Two distinct but related packages
1) ParaScale
    - Contains instructional codes
    - Demonstrates Threads, actors, futures, parallel collections
    - Dispatcher-Worker pattern example

2) ParaBond
    - Contains the bond pricing application 
    - This will be scaled out later on 
    - NoSQL Database (MongoDB)
    - Bond portfolio valuation using ScaleUp 
        - parallel collections and map reduce futures 
    - We will extend this to SCALE OUT
        - Using remote Actors 

## Summary 

- Threads are the basis of all JVM Parallelism
- Green Threads managed by the JVM
- HyperThreads managed by the Hardware
- Starting threads is easy
- Synchronizing is hard (Hazards - Deadlock, Race Condition, Starvation)
- Dispatcher-Worker is a powerful pattern for parallel processing
- Actors are concurrent objects depdent on thredas
- ParaScale is used for instruction and research 









