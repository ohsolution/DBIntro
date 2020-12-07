// Note: You need to add additional member variables and functions as you need.

#define NUM_KEYS 10 
// NUM_KEYS should be set to make each tree node as large as 4KB. 
// But for this assignment, we will set the degree of node small to make debugging and grading easy
// Please do not change this number. 
// In practice, DBMS employs 'slotted page structure' to store variable-length records in B+tree.
// But the purpose of this assignment is to make students familiar with B-tree itself. 

enum NodeType {
	ROOT,
	INTERNAL,
	LEAF
};

struct pos;

class BTreeInternalNode;

class BTreeNode{   
   protected:
	NodeType type;
	long long keys[NUM_KEYS+1];
	BTreeNode * child[NUM_KEYS+2];
	int nk;
   public:
	BTreeNode();
	virtual ~BTreeNode() {}
	NodeType getNodeType();
	pos find(long long _value);	
	void showNode(int _pad); // For debuging
	BTreeInternalNode * parent;
};

class BTreeInternalNode:public BTreeNode{	
   public:
	BTreeInternalNode();
	~BTreeInternalNode();
	BTreeNode * insert(long long _value,BTreeNode * _ptr);
	void setinit(int _val,BTreeNode * _ptr1,BTreeNode * _ptr2);
	BTreeInternalNode * split();
};

class BTreeLeafNode:public BTreeNode{
   private:
	BTreeLeafNode* right_sibling;
   public:
	BTreeLeafNode();
	~BTreeLeafNode();
	void printLeafNode(); // print all keys in the current leaf node, separated by comma.
	bool printrange(int _upper_limit,int _lo,int _idx);
	BTreeLeafNode* getRight_sibling();
	void setRight_sibling(void * p);
	BTreeNode * insert(long long _value);
	BTreeLeafNode * split();
	BTreeLeafNode* revptr;
};


class BTree{  
    private:
	BTreeNode *root;
    public:
	// You are not allowed to change the interfaces of these public methods.
	BTree(); 
	~BTree(); 
	void insert(long long value);  
	//	void remove(long long value);
	void printLeafNode(long long value);
	// find the leaf node that contains 'value' and print all values in the leaf node.
	void pointQuery(long long value); 
	// print the found value or "NOT FOUND" if there is no value in the index
	void rangeQuery(long long low, long long high); 
	// print all found keys (low <= keys < high), separated by comma (e.g., 10, 11, 13, 15\n) 
	void showTree(); // For debug
};

struct pos
{
	BTreeLeafNode * leaf;	
	long long val;
	int p;
};