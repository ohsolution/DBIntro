# DATA STORAGE STRUCTURES


## FILE ORGANIZATION

_Fixed-length Records_

- approach with fixed offset

- delete opreation little bit cost
    
    1. move record

    2. link free records on a free list

_Variable-length Records_

- multiple record types in a file

- columns types taht allow variable lengths like varchar

* one side having <offset,length> pair other side have data


-> slotted page

--- Block Header ------- Free Space --- Records
   
save end of free space



## Organization of Records in Files

_Heap_

* records can be placed anywhere in the file

* records usually do not move once allocate

* using free space map saving remain percentage with oct value

_Sequential_

* records in the file are ordered by a search-key

* deletion - use pointer chain
  insertion - if free space then insert else insert in overflow block

## INDEX

ordered indices <-> hash indices


_ordered indices_

* dense index ( every key : row )

* sparse index ( some key : row )

* secondary index ( key : bucket : row )
    has to be dense

* Multilevel index

    - outer index (sparse)
    - inner index (basic index file)

_B+Tree_

- attribute
    
    1. Balanced tree
    2. each node has children ceil(n/2) to n <except root,leef>
    3. leef node has ceil((n-1)/2) to n-1 values
    4. having some special case
        > if the root is not a leaf, it has at least 2 children
        > if the root is a leaf it can have 0 to n-1 values


        


    














