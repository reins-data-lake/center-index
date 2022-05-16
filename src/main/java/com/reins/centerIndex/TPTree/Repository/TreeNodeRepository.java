package com.reins.centerIndex.TPTree.Repository;

import com.reins.centerIndex.TPTree.TreeNode;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TreeNodeRepository extends MongoRepository<TreeNode,String> {

}
