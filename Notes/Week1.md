# Parallel Processing 

Parallel Processing and its limitations

## What is Parallel Processing 

Use of concurrency to speed up an application 

Concurrency in 
    - Hardware (multicore, SMT)
    - Software (algorithms, languages)
Goal: Exploit these as best as possible 

## How is it used?

Machine learning is a huge application 

## Why study parallel processing?

Every modern CPU is multicore
No free lunch dilemma
    - In the past we waited for faster processors (free lunch)
    - Need to exploit multiple cores to improve performance

## Parallel vs Distributed Processing

Parallel: 
    - Tight Coupling 
    - Multicore
    - Scale up 

Both:
    - Threading 
    - Speedup
    - Throughput 

Distributed:
    - Reliability
    - Loose coupling
    - Scale out 

## Limitations 

Hardware:
    - Number of cores
    - Memory latency
    - Pathlength
    - Network bandwidth (for distributed)

Algorithms:
    - Bottleneck analysis
    - Concurrency hazards

Software:
    - Languages
    - Models
    - Debugging
    - Testing 

## Von Neumann Bottleneck

The shared bus between program memory and data memory leads to the bottleneck
    - The limited throughput [data transfer rate], between the CPU and the memory compared to the amount of memory
    - Because the single bus can only access one of the two classes of memory at a time, throughput is lower than the rate at which the CPU can work 
    - This seriously limits the effective processing speed when the CPU is required to perform minimal processing on large amounts of data 
    - As CPU speed and memory size continually increase, this bottleneck gets worse 

Mitigations:
    - Cache between CPU and main memory
    - Branch predictor algorithms and logic

The problem can also be sidestepped somewhat by using parallel computing
    - Uses a single cached instruction on large amounts of data 
    - Non-uniform memory access architecture (processors can access its own local memory faster than non-local memory)

## Speed up and Efficiency

T1 = time on uniprocessor
TN = Time on system with N processors
R = T1 / TN (the speed up, ratio of single threaded vs multithreaded)
e = R / N = Efficiecny
overhead = 1 - efficiency

## Algorithm Limits

G Amdahl proposed there were limits of how much you could effectively parallelize

## Amdahl's Law 

States that parallel computing with *many* processors is only useful for highly parallelizable programs 

s = fraction of task non-parallelizable
p = fraction of task parallelizable 

So for a uniprocessor machine:
    1 = p + s
    T1 = s + p = 1

What is TN for a N processor system?
    TN = s + (p / N) 

What is R (speed up?)
    R = T1 / TN
    R = 1 / (s + (p / N))

as N approaches Inifinty : 
    1 / s     (Amdah's law)

## Amdahl's Assumptions

T1 = s + p = 1 
    Probelm size is fixed!!

1. Usually run a bigger problem on bigger machines

2. Time is constant, not problem size 

## Gustafson's Law 

In contrast, Gustafson's Law gives the theoretical speedup in latency of the execution of a task at *fixed* execution time that can be expected of a system whose resources are improved. 

Addresses the shortcomings of Amdahl's law which is based on the assumption of a fixed problem size, i.e. Programmers will design larger problems for larger systems and execute in the same time. 

This caused a shift in research goals to select or reformulate problems so that solving a large problem in the same amount of time would be possible. It implies that you can counter the serial portion of an algorithm by just using larger datasets for parallelism. 

TN = 1
T1 = s + p * N
R = T1 / TN 
R = s + (p * N)
    States there is a linear speedup, because of the problem scaling !


Efficiency isn't very high
    e = R / N 
    e = s / (N + p)

## Functional Programming

1) Relies on functions rather than State (imperative programming)
    f(x) -> y 
    X, Y are read only copies, nothing to lock! 

2) Functions are first-class objects (f(x) where x can be a function)
    Allows us to use higher order functions

3) Follows lambda calculus 
    Developed in the 1930s, equivalent to turinng machine in computability and its mathematically expressive 

Scales well! 

Supported by multicores 

## Scala 

Blends object and functional styles

Four approaches to parallel programming

JVM Language

## Scala Parallelism

Task Level
    1. Threads
    2. Actors
    3. Futures/Promises 
Data Level
    4. Parallel Collections 


## Thinking Parallel 

Map and Reduce are higher order functuions because they accept functions and apply them to a set 

Thinking functionally is thinking parallely! 

## ParaScale

From GitHub.com 

## Summary 

1. Amdahl's Law assumes problem size fixed and speedup is limited by the serial component ( 1 / s)

2. Gustafson's Law assumes time is fixed and speedup is linear in N, number of cores

3. FP is well suited to parallel programming

4. Scala is a JVM language that supports four concurrency models

5. ParaScale is a base project for exploring parallelism 
