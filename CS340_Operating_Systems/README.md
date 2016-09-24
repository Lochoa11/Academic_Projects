#Operating Systems Projects

These projects we were to program threads so that it follow the following story line.
In project 2 there are more emphasis on Semaphores.

##Sky-blue and the gnomes

Once upon a time, there was a princess named Sky-blue. The princess’s stepmother, the Queen was very mean and vain. She was jealous because she wanted to be known as "the fairest in the land," and Sky-blue’s beauty surpassed her own. One day, Sky-blue couldn’t take it anymore and ran away to a fairy garden. In the garden she found a small cabin. In the cabin there were num_gnomes little beds, one table of table_size size, one love seat, one recliner and a big 50” flat screen TV. However the cabin was very dirty and failed to provide a pleasant environment to relax and watch a good movie.
Sky-blue decided to start cleaning and cooking. Soon she got exhausted and fell asleep (use sleep(long time) such that she will be interrupted later on by the gnomes)

In the evening, the gnomes arrived home from the diamond-mine. They commuted in different ways (sleep of random time) but they always waited for each other, so that all of them should be at the door before entering the cabin (use yield() to have them release the CPU if it is not the time to get in the cabin). The last gnome to arrive (you might want to use a counter. It should be accessed from a synchronized method to ensure mutual exclusion), opens the door allowing everyone to get in (you might want to use a boolean variable). Once they entered the cabin, they were mesmerized by how beautiful and clean their house looked. They could immediately see the TV-remote, which was a miracle. It didn’t take them too long to find Sky-blue. The gnomes then started to make a lot of noise trying to wake up the princess (send an interrupt to the princess. To avoid race conditions you might want to send only one interrupt). Next, they all busy wait for the princess to let them know how she got there and who she was.

Time for dinner came and the gnomes got very hungry. While Sky-Blue was setting up the table, the gnomes are busy waiting for the table to be ready. Sky-Blue, will let them know when the table is ready. The gnomes are very hungry and they rush to take a seat at the table (simulate the rush by having the gnome increasing its priority, Next he will sleep(random time) and when he resumes, he will reset his priority back to the default value (use getPriorty( ) and setPriority()). Once the dinner ends, the gnomes go to bed (sleep of random time).
Sky-Blue will sleep too (busy waiting) and in the morning the last gnome to wake up will also wake her up (you might want to use a counter accessed in a mutual exclusion fashion). In the morning, the gnomes brush their teeth, take a shower and line up for Sky-Blue. In exchange for a diamond, she gives them a kiss and their lunch box (gnomes are kissed by Sky-blue in FCFS order of their line-up (you might want to use an array, vector ,...to enforce the order).
Once Sky-blue is done kissing all the gnomes she will busy wait for them to leave.

Now the gnomes are ready to go to work. They will leave the house in the following order:
g1 g3 g5 ....g11 g10 g8......g0
(use isAlive() and join( ) to enforce this order). Gnome 0 doesn’t wait for anybody. He will just
have a short sleep and terminate.
The last gnome to leave lets Sky-blue know that she can terminate as well.


##The End
Synchronize the gnomes and the princess threads based on the given story. Closely follow the story and the given implementation details.