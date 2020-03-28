# Actors

## Threads
Strengths:
    - Simplicity
    - Portability
    - Universality

Weaknesses:
    - Depdend on Shared Memory
    - Not Scalable
    - Too Low Level 

## Divide and Conquor Algorithm 

Key to all parallel processing algorithms

1) Decompose problem into smaller tasks
2) Distribute tasks to parallel processors
3) Wait and aggregate results 


## Actors

Originally invented for AI in the 1970s

They are "Concurrent Objects" 

If on different JVMs, they're called _remote actors_

Communicate only by message passing 

Each Actor has a Mailbox

Processes messages in the order they're received 

## Actor Model 

Actor A sends Messages to Actor B

Actor B dequeues messages from its mailboxc and processes them one-by-one in a single threaded loop

This allows A to be non-blocking

## Dispatcher-Worker with Actors

The dispatcher is like Actor A above, Workers are like worker B above

## Consumer Producer Actors

Well suited to the Actor Model 

*Can calculate T1 by summing all the individual Worker Task times*

## Actorlike

Collection of Scala Code that simulates Actors using Threads  in ParaScale (parascale.thread.actorlike._) only *scales up*

## Last Actors

Collection of scala code in parascale (parascale.actor.last._)

Pure scala, no config files 

Supports *scale out* with remote actors

1) Actor is a Runnable 
2) Every Actor has a Mailbox
3) Mailbox is generic (i.e. parameterized by type T)
    - We can specify custome mailbox types or what message types we want to use
4) Message sent to target actor with reference `!` 
5) Sending actor does not wait for processing of T by recipient
6) Relay forwards message to remote Actor in different JVM
7) Remote receives messages from Relay 
8) Remote forwards message to receiving actor to which it is bound

With Actors, you don't invoke start, you just Construct

Last Actor Bootstraps itself 

Actor inherently ScalesUp in the JVM 

Relay, Remote as actors Extend Actor 

## Actor Act Method

Automatically invoked on Actor Constructor

Typlically in Act, invoke Receive() 
    This is blocking, so it wait's for a message to arrive 

Then decode the message using a match Statement 

## Actors: Declaring and Instantiating 

1) Extend Actor
2) Override act()
3) Do new on Actor subclass you extended
    a) Control transfers immediately to act()
    b) Invoke receieve-match

## Sending (Local) Messages

1) Instantiate a Case Class Instance, m 
2) Instantiate a target actor, b 
3) Send the message m to actor b  as `b ! m`

## Sending (remote) Messages

Same as local but
1) Message m must be serialiable 
    Better, a serializable Case Class
2) Instantiate Relay actor with host:port, send m to this actor
3) On remote host, instantiate actor b bound to an instance of remote actor with port

## Sending Replies

In Receive-match scope `sender ! m`

## Perfect Numbers

Over the next few weeks..
    We define Perfect Numbers
    Develop Serial T1 Algorithm
    Develop scale-up TN Algorithm
    Develop scale-out TN Algorithm using Dispatcher-Worker backed by remote actors

A perfect number if the sum of its factors equals the number
    6 is perfect since 1+2+3 == 6
    28 is a perfect number since 1+2+4+7+14 == 28 
    Sum of factors x 2  since we include the number itself


## PerfectNumberFinder.scala

Uses higher-order function `ask` from parascale.future.perfect
    Invokes the perfect number predicate
    handles reporting 
    Returns T1 (dt)

## Perfect Number Predicate

Returns true if the number is perfect; False otherwise
    Invokes sumOfFactors() : Returns sum of factors
    Checks if Sum of Factors is 2* Candidate
        (Because we include the number itself as a factor)

## Parallel Perfect Number Finder

Why is this a good problem to parallelize?
     Because we can Decompose the Range! 
     This is the Divide and Conquer concept
     Easy to split work across processors 

     Worker Does Partial Sum of factors in its range 
     Dispatcher sums the partial sums

Similar to the Add-Multiply example 

## Summary 

- Actors are Concurrent Objects
- Actors Communicate via Message Passing 
- Received Messages processed in single-threaded loop
- Messages processed in order received 
- Sender does not wait for receiver to process message
- Last Actors backed by threads 
- Last actors support Scale Up and Scale Out



