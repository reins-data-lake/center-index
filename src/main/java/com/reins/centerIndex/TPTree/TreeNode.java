package com.reins.centerIndex.TPTree;

import java.util.HashMap;
import java.util.Map;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// not used
@Document(collection = "TreeNode")
@Data
public class TreeNode{
    @Id
    String uid;
    String[] childNodes;
    String parentId;
    Map<Long, StaticValue> staticValueMap;//每个node都会有staticValue,
    public TreeNode(){
        childNodes = new String[TreeConfig.BlockLength];
        parentId = TreeConfig.TreeRootId;
        staticValueMap = new HashMap<Long, StaticValue>();
    }
    public TreeNode(String _parentId){
        childNodes = new String[TreeConfig.BlockLength];
        parentId = _parentId;
        staticValueMap = new HashMap<Long, StaticValue>();
    }
}