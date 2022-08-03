# jvm
## deadlock（死锁）
jps -l
```bash
18228 org.jetbrains.jps.cmdline.Launcher
18069 org.jetbrains.idea.maven.server.RemoteMavenServer36
18229 deadlock.DeadLockDemo
18054
18233 jdk.jcmd/sun.tools.jps.Jps
```
jstack 18074
```bash
2022-08-03 09:00:34
Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.201-b09 mixed mode):

"Attach Listener" #16 daemon prio=9 os_prio=31 tid=0x00007fe7b9890800 nid=0xa707 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"DestroyJavaVM" #15 prio=5 os_prio=31 tid=0x00007fe7b687c000 nid=0xf03 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"JPS event loop" #10 prio=5 os_prio=31 tid=0x00007fe7b299b800 nid=0x5803 runnable [0x000070000c676000]
   java.lang.Thread.State: RUNNABLE
	at sun.nio.ch.KQueueArrayWrapper.kevent0(Native Method)
	at sun.nio.ch.KQueueArrayWrapper.poll(KQueueArrayWrapper.java:198)
	at sun.nio.ch.KQueueSelectorImpl.doSelect(KQueueSelectorImpl.java:117)
	at sun.nio.ch.SelectorImpl.lockAndDoSelect(SelectorImpl.java:86)
	- locked <0x00000007944cd9d0> (a io.netty.channel.nio.SelectedSelectionKeySet)
	- locked <0x00000007944e3b80> (a java.util.Collections$UnmodifiableSet)
	- locked <0x00000007944d48c0> (a sun.nio.ch.KQueueSelectorImpl)
	at sun.nio.ch.SelectorImpl.select(SelectorImpl.java:97)
	at sun.nio.ch.SelectorImpl.select(SelectorImpl.java:101)
	at io.netty.channel.nio.SelectedSelectionKeySetSelector.select(SelectedSelectionKeySetSelector.java:68)
	at io.netty.channel.nio.NioEventLoop.select(NioEventLoop.java:810)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:457)
	at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:989)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at java.lang.Thread.run(Thread.java:748)

"Service Thread" #9 daemon prio=9 os_prio=31 tid=0x00007fe7b2820000 nid=0x5503 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C1 CompilerThread3" #8 daemon prio=9 os_prio=31 tid=0x00007fe7b0808800 nid=0x3e03 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread2" #7 daemon prio=9 os_prio=31 tid=0x00007fe7ae81a800 nid=0x3c03 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread1" #6 daemon prio=9 os_prio=31 tid=0x00007fe7ae81a000 nid=0x4103 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread0" #5 daemon prio=9 os_prio=31 tid=0x00007fe7af80c800 nid=0x3b03 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Signal Dispatcher" #4 daemon prio=9 os_prio=31 tid=0x00007fe7b300c800 nid=0x4303 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Finalizer" #3 daemon prio=8 os_prio=31 tid=0x00007fe7ae812000 nid=0x3203 in Object.wait() [0x000070000bd58000]
   java.lang.Thread.State: WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	- waiting on <0x0000000794434a70> (a java.lang.ref.ReferenceQueue$Lock)
	at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:144)
	- locked <0x0000000794434a70> (a java.lang.ref.ReferenceQueue$Lock)
	at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:165)
	at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:216)

"Reference Handler" #2 daemon prio=10 os_prio=31 tid=0x00007fe7b1037000 nid=0x4c03 in Object.wait() [0x000070000bc55000]
   java.lang.Thread.State: WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	- waiting on <0x000000079443c500> (a java.lang.ref.Reference$Lock)
	at java.lang.Object.wait(Object.java:502)
	at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
	- locked <0x000000079443c500> (a java.lang.ref.Reference$Lock)
	at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)

"VM Thread" os_prio=31 tid=0x00007fe7b281a800 nid=0x3003 runnable

"GC task thread#0 (ParallelGC)" os_prio=31 tid=0x00007fe7af80c000 nid=0x2007 runnable

"GC task thread#1 (ParallelGC)" os_prio=31 tid=0x00007fe7b3811000 nid=0x1c03 runnable

"GC task thread#2 (ParallelGC)" os_prio=31 tid=0x00007fe7b3811800 nid=0x1e03 runnable

"GC task thread#3 (ParallelGC)" os_prio=31 tid=0x00007fe7b3812800 nid=0x2a03 runnable

"GC task thread#4 (ParallelGC)" os_prio=31 tid=0x00007fe7b3813000 nid=0x5303 runnable

"GC task thread#5 (ParallelGC)" os_prio=31 tid=0x00007fe7b1009800 nid=0x5103 runnable

"GC task thread#6 (ParallelGC)" os_prio=31 tid=0x00007fe7b3813800 nid=0x2c03 runnable

"GC task thread#7 (ParallelGC)" os_prio=31 tid=0x00007fe7b3814000 nid=0x2e03 runnable

"GC task thread#8 (ParallelGC)" os_prio=31 tid=0x00007fe7b3815000 nid=0x4f03 runnable

"GC task thread#9 (ParallelGC)" os_prio=31 tid=0x00007fe7b100a000 nid=0x4e03 runnable

"VM Periodic Task Thread" os_prio=31 tid=0x00007fe7af80d800 nid=0xa903 waiting on condition

JNI global references: 458

bushixiandeMacBook-Pro:~ yuell102$ jps
18228 Launcher
18069 RemoteMavenServer36
18229 DeadLockDemo
18054
18232 Jps
bushixiandeMacBook-Pro:~ yuell102$ jps -l
18228 org.jetbrains.jps.cmdline.Launcher
18069 org.jetbrains.idea.maven.server.RemoteMavenServer36
18229 deadlock.DeadLockDemo
18054
18233 jdk.jcmd/sun.tools.jps.Jps
bushixiandeMacBook-Pro:~ yuell102$ jstack 18229
2022-08-03 09:07:44
Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.201-b09 mixed mode):

"Attach Listener" #16 daemon prio=9 os_prio=31 tid=0x00007fb607808800 nid=0x450b waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"DestroyJavaVM" #15 prio=5 os_prio=31 tid=0x00007fb60900b000 nid=0xf03 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Thread-1" #14 prio=5 os_prio=31 tid=0x00007fb60a01b000 nid=0x5b03 waiting for monitor entry [0x000070000be27000]
   java.lang.Thread.State: BLOCKED (on object monitor)
	at deadlock.DeadLock.run(DeadLockDemo.java:41)
	- waiting to lock <0x000000076aed9a68> (a java.lang.Object)
	- locked <0x000000076aed9a78> (a java.lang.Object)
	at java.lang.Thread.run(Thread.java:748)

"Thread-0" #13 prio=5 os_prio=31 tid=0x00007fb608817800 nid=0x5903 waiting for monitor entry [0x000070000bd24000]
   java.lang.Thread.State: BLOCKED (on object monitor)
	at deadlock.DeadLock.run(DeadLockDemo.java:32)
	- waiting to lock <0x000000076aed9a78> (a java.lang.Object)
	- locked <0x000000076aed9a68> (a java.lang.Object)
	at java.lang.Thread.run(Thread.java:748)

"Service Thread" #12 daemon prio=9 os_prio=31 tid=0x00007fb606045800 nid=0xa603 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C1 CompilerThread3" #11 daemon prio=9 os_prio=31 tid=0x00007fb60d808800 nid=0xa703 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread2" #10 daemon prio=9 os_prio=31 tid=0x00007fb607840000 nid=0x5603 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread1" #9 daemon prio=9 os_prio=31 tid=0x00007fb608818800 nid=0x5503 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread0" #8 daemon prio=9 os_prio=31 tid=0x00007fb6098be800 nid=0x3c03 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"JDWP Command Reader" #7 daemon prio=10 os_prio=31 tid=0x00007fb60601c000 nid=0x3e03 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"JDWP Event Helper Thread" #6 daemon prio=10 os_prio=31 tid=0x00007fb609817800 nid=0x3903 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"JDWP Transport Listener: dt_socket" #5 daemon prio=10 os_prio=31 tid=0x00007fb607820000 nid=0x3f07 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Signal Dispatcher" #4 daemon prio=9 os_prio=31 tid=0x00007fb608815000 nid=0x4003 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Finalizer" #3 daemon prio=8 os_prio=31 tid=0x00007fb60880f000 nid=0x4a03 in Object.wait() [0x000070000b0fd000]
   java.lang.Thread.State: WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	- waiting on <0x000000076ab08ed0> (a java.lang.ref.ReferenceQueue$Lock)
	at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:144)
	- locked <0x000000076ab08ed0> (a java.lang.ref.ReferenceQueue$Lock)
	at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:165)
	at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:216)

"Reference Handler" #2 daemon prio=10 os_prio=31 tid=0x00007fb60781c000 nid=0x3103 in Object.wait() [0x000070000affa000]
   java.lang.Thread.State: WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	- waiting on <0x000000076ab06bf8> (a java.lang.ref.Reference$Lock)
	at java.lang.Object.wait(Object.java:502)
	at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
	- locked <0x000000076ab06bf8> (a java.lang.ref.Reference$Lock)
	at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)

"VM Thread" os_prio=31 tid=0x00007fb60880c000 nid=0x4d03 runnable

"GC task thread#0 (ParallelGC)" os_prio=31 tid=0x00007fb608008800 nid=0x1c07 runnable

"GC task thread#1 (ParallelGC)" os_prio=31 tid=0x00007fb606017000 nid=0x2003 runnable

"GC task thread#2 (ParallelGC)" os_prio=31 tid=0x00007fb606809800 nid=0x1f03 runnable

"GC task thread#3 (ParallelGC)" os_prio=31 tid=0x00007fb60680a800 nid=0x2a03 runnable

"GC task thread#4 (ParallelGC)" os_prio=31 tid=0x00007fb608808800 nid=0x5403 runnable

"GC task thread#5 (ParallelGC)" os_prio=31 tid=0x00007fb608809000 nid=0x5203 runnable

"GC task thread#6 (ParallelGC)" os_prio=31 tid=0x00007fb609008800 nid=0x2c03 runnable

"GC task thread#7 (ParallelGC)" os_prio=31 tid=0x00007fb608809800 nid=0x2e03 runnable

"GC task thread#8 (ParallelGC)" os_prio=31 tid=0x00007fb606017800 nid=0x5003 runnable

"GC task thread#9 (ParallelGC)" os_prio=31 tid=0x00007fb606018800 nid=0x3003 runnable

"VM Periodic Task Thread" os_prio=31 tid=0x00007fb606815800 nid=0xa503 waiting on condition

JNI global references: 1438


Found one Java-level deadlock:
=============================
"Thread-1":
  waiting to lock monitor 0x00007fb60d00b0c8 (object 0x000000076aed9a68, a java.lang.Object),
  which is held by "Thread-0"
"Thread-0":
  waiting to lock monitor 0x00007fb60d00d8a8 (object 0x000000076aed9a78, a java.lang.Object),
  which is held by "Thread-1"

Java stack information for the threads listed above:
===================================================
"Thread-1":
	at deadlock.DeadLock.run(DeadLockDemo.java:41)
	- waiting to lock <0x000000076aed9a68> (a java.lang.Object)
	- locked <0x000000076aed9a78> (a java.lang.Object)
	at java.lang.Thread.run(Thread.java:748)
"Thread-0":
	at deadlock.DeadLock.run(DeadLockDemo.java:32)
	- waiting to lock <0x000000076aed9a78> (a java.lang.Object)
	- locked <0x000000076aed9a68> (a java.lang.Object)
	at java.lang.Thread.run(Thread.java:748)

Found 1 deadlock.
```