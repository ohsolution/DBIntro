// Please implement most of your source codes here. 
#include "BTree.h"
#include <bits/stdc++.h>

using namespace std;

BTreeNode::BTreeNode()
{
    nk = 0;
}

BTreeRootNode::BTreeRootNode():BTreeNode()
{
    type = ROOT;
    for(int i=0;i<NUM_KEYS+1;++i) child[i] = NULL;
}

BTreeRootNode::~BTreeRootNode()
{
    for(int i=0;i<nk;++i) delete child[i];
}

BTreeInternalNode::BTreeInternalNode():BTreeNode()
{
    type = INTERNAL;
    for(int i=0;i<NUM_KEYS+1;++i) child[i] = NULL;
}

BTreeInternalNode::~BTreeInternalNode()
{
    for(int i=0;i<nk;++i) delete child[i];
}

BTreeLeafNode::BTreeLeafNode():BTreeNode()
{
    type = LEAF;
    right_sibling = NULL;
}

BTreeLeafNode::~BTreeLeafNode()
{

}

BTree::BTree() {root = new BTreeRootNode();}

BTree::~BTree() {delete root;}

NodeType BTreeNode::getNodeType(){return type;}

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

BTreeLeafNode* BTreeLeafNode::getright_sibling() {return right_sibling;}

void BTreeLeafNode::printLeafNode()
{
    for(int i=0;i<nk-1;++i) cout << keys[i]<<", ";
    cout<<keys[nk-1];
}

void BTreeLeafNode::printrange(int upper_limit,int idx=0)
{
    for(int i=idx;i<nk&&keys[i]<upper_limit;++i)
    {
        if(i!=idx) cout<<", ";
        cout << keys[i];
    }
}

void BTree::pointQuery(long long value)
{
    pos target = root->find(value);

    if(target.val==value) cout << target.val<<"\n";
    else cout << "NOT FOUND\n";
}

void BTree::rangeQuery(long long low,long long high)
{
    pos tlow = root->find(low);
    pos thigh = root->find(high);

    tlow.leaf->printrange(thigh.val,tlow.p);

    while(tlow.leaf != thigh.leaf)
    {
        tlow.leaf = tlow.leaf->getright_sibling();
        tlow.leaf->printrange(thigh.val);
    }
}

void BTree::printLeafNode(long long value)
{
    pos target = root->find(LONG_LONG_MIN);

    while(target.leaf->getright_sibling())
    {
        target.leaf->printLeafNode();
        target.leaf = target.leaf->getright_sibling();
        cout<<", ";
    }
    target.leaf->printLeafNode();
    cout<<"\n";
}

void BTree::insert(long long value)
{
    
}

