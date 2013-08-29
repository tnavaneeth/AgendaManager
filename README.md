AgendaManager
=============

Rules based Agenda Manager

Language: Java

The program gets the absolute path from the user. Once the user provides the filepath, file is
obtained & started process each line of the input file. The first line will be read & parses the
names of the rule & priority value. After the whole line is read, a heap will be built using
buildheap() & heapify() methods. After building the heap, the higher priority rule will be found
out & displayed as output through Heap_extractmax() method. The displayed element will be
removed from the heap.
Similarly rules present in further lines of input file will be added into the heap built. At the end
of adding all elements in each line of input file, the highest priority rule will be displayed. After
the input file is fully read, the rules will be displayed as per the highest priority until all the rules
are processed.
