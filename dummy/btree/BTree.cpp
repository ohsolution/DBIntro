// Please implement most of your source codes here. 
#include "BTree.h"
#include <bits/stdc++.h>

using namespace std;

/*
// debug

string arr[3] = {"ROOT","IM","LEAF"};

void BTreeNode::showNode(int pad =0)
{
    string padding = "";
    for(int i=0;i<pad;++i) padding +=" "; 

    cout << endl;
    cout << padding;
    cout << "type : " << arr[type]<<endl;
    cout << padding; 
    cout << "nk : " << nk << endl;
    cout << padding;
    for(int i=0;i<nk;++i) cout<< keys[i]<<" ";

    if(type != 2) for(int i=0;i<nk+1;++i) child[i]->showNode(pad+4);

    cout << endl;
}


void BTree::showTree()
{
    root->showNode();
}
*/

BTreeNode::BTreeNode()
{
    nk = 0;
    for(int i=0;i<NUM_KEYS+1;++i) keys[i] = -1;
}

BTreeInternalNode::BTreeInternalNode():BTreeNode()
{
    parent = NULL;
    type = INTERNAL;
    for(int i=0;i<NUM_KEYS+1;++i) child[i] = NULL;
}

BTreeInternalNode::~BTreeInternalNode()
{
    for(int i=0;i<nk;++i) delete child[i];
}

BTreeLeafNode::BTreeLeafNode():BTreeNode()
{
    parent = NULL;
    type = LEAF;
    right_sibling = NULL;
    revptr = NULL;
}

BTreeLeafNode::~BTreeLeafNode(){;}

BTree::BTree() {root = new BTreeLeafNode();}

BTree::~BTree() {delete root;}

/* getter and setter*/

NodeType BTreeNode::getNodeType(){return type;}

BTreeLeafNode* BTreeLeafNode::getRight_sibling() {return right_sibling;}

void BTreeLeafNode::setRight_sibling(void* p) 
{
    right_sibling=(BTreeLeafNode*)p;
    right_sibling->revptr = (BTreeLeafNode*)this;
}

/* search print*/

pos BTreeNode::find(long long value)
{
    if(getNodeType() == LEAF)
    {
        pos ret;
        ret.leaf = (BTreeLeafNode*)this;
        ret.p = lower_bound(keys,keys+nk,value)-keys;

        ret.val = (ret.p != nk) ? keys[ret.p] : -1;

        return ret;
    }

    return child[upper_bound(keys,keys+nk,value)-keys]->find(value);
}

void BTreeLeafNode::printLeafNode()
{
    for(int i=0;i<nk-1;++i) cout << keys[i]<<", ";
    cout<<keys[nk-1];
}

bool BTreeLeafNode::printrange(int upper_limit,int lo,int idx=0)
{
    bool ret = true;
    for(int i=idx;i<nk&&(keys[i]<upper_limit);++i)
    {
        if(keys[i] == lo) continue;
        
        if(!ret) cout<<", ";
        cout << keys[i];
        ret &= false;
    }
    return ret;
}

void BTree::pointQuery(long long value)
{
    pos target = root->find(value);

    if(target.val==value) cout << target.val<<"\n";
    else cout << "NOT FOUND\n";
}

void BTree::rangeQuery(long long low,long long high)
{
    pos tlow = root->find(low-1);
    pos thigh = root->find(high);

    bool first= tlow.leaf->printrange(high,low-1,tlow.p);

    while(tlow.leaf != thigh.leaf)
    {                
        tlow.leaf = tlow.leaf->getRight_sibling();

        if(first);
        else if(tlow.leaf != thigh.leaf || thigh.p) cout <<", ";
        first &= tlow.leaf->printrange(high,low-1);
    }
    cout <<"\n";
}

void BTree::printLeafNode(long long value)
{
    pos target = root->find(value);
    if(target.val == value)
    {
        target.leaf->printLeafNode();
        cout<<"\n";
    }
}

/* insert */
void BTreeInternalNode::setinit(int val,BTreeNode * ptr1,BTreeNode * ptr2)
{
    type = ROOT;
    nk = 1;
    keys[0]= val;
    child[0] = ptr1;
    child[1] = ptr2;
}

BTreeInternalNode * mk_root(int val,BTreeNode * ptr1,BTreeNode * ptr2)
{
    BTreeInternalNode * ret = new BTreeInternalNode();
    ret->setinit(val,ptr1,ptr2);
    return ret;
}

BTreeLeafNode * BTreeLeafNode::split()
{
    BTreeLeafNode * ret = new BTreeLeafNode();

    int nnk = (nk>>1)+1;

    for(int i=nnk;i<nk;++i)
    {        
        ret->keys[(ret->nk)++] = keys[i];        
    }

    nk = nnk;
    return ret;
}

BTreeInternalNode * BTreeInternalNode::split()
{
    BTreeInternalNode * ret = new BTreeInternalNode();

    int nnk = (nk>>1);

    for(int i=nnk+1;i<nk;++i)
    {
        ret->child[ret->nk] = child[i];
        ret->child[ret->nk]->parent = ret;
        ret->keys[(ret->nk)++] = keys[i];
    }
    ret->child[ret->nk] = child[nk];
    ret->child[ret->nk]->parent = ret;
    
    nk = nnk;
    return ret;
}

BTreeNode * BTreeInternalNode::insert(long long value,BTreeNode * ptr)
{
    BTreeNode * ret = NULL;

    int idx = nk;
    keys[nk]=value;    
    child[(nk++)+1] = ptr;        

    void * nxt = ((BTreeLeafNode*)child[idx])->getRight_sibling();
    BTreeLeafNode* pre = ((BTreeLeafNode*)child[0])->revptr;

    while(idx && (keys[idx-1] > keys[idx])) 
    {
        swap(keys[idx-1],keys[idx]);
        swap(child[idx],child[idx+1]);
        --idx;
    }

    if(child[idx]->getNodeType()==LEAF)
    {        
        for(int i=idx;i<nk-1;++i)
        {
            ((BTreeLeafNode *)child[i])->setRight_sibling(child[i+1]);
        }
        
        if(nxt) ((BTreeLeafNode *)child[nk-1])->setRight_sibling(nxt);
        if(pre) pre->setRight_sibling(child[0]);

    }
    
    if(nk == NUM_KEYS+1)
    {        
        BTreeInternalNode * tmp = split();

        if(parent)
        {
            BTreeNode* pck = parent->insert(keys[nk],tmp);
            if(pck) ret = pck;
        }
        else // root is full
        {
            parent = mk_root(keys[nk],(BTreeNode*)this,(BTreeNode*)tmp);            
            ret = parent;
            type = INTERNAL;
        }

        tmp->parent = parent;
    }

    return ret;
}

BTreeNode * BTreeLeafNode::insert(long long value)
{
    BTreeNode * ret = NULL;

    int idx = nk;
    keys[nk++]=value;    

    while(idx && (keys[idx-1] > keys[idx])) 
    {
        swap(keys[idx-1],keys[idx]);
        --idx;
    }

    if(nk == NUM_KEYS+1)
    {        
        BTreeLeafNode * tmp = split();

        if(right_sibling) tmp->setRight_sibling(right_sibling);
        setRight_sibling(tmp);

        if(parent)
        {
            BTreeNode* pck = parent->insert(tmp->keys[0],tmp);
            if(pck) ret = pck;
        }
        else // root is full
        {
            parent = mk_root(tmp->keys[0],(BTreeNode*)this,(BTreeNode*)tmp);                        
            ret = parent;
        }

        tmp->parent = parent;
    }
    
    return ret;
}

void BTree::insert(long long value)
{
    pos target = root->find(value);
    void * pck = target.leaf->insert(value);
    if(pck) root = (BTreeNode*)pck; 
}

