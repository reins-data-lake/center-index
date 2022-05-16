package com.reins.centerIndex.TPTree.Utils;

import com.reins.centerIndex.TPTree.Repository.TreeNodeRepository;
import com.reins.centerIndex.TPTree.TreeConfig;
import com.reins.centerIndex.TPTree.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TreeNodeUtil {
    @Autowired
    TreeNodeRepository treeNodeRepository;

    public int[] timeOffsetToIndex(long time){

        long timeOffset = time - TreeConfig.MinimumTime;
        long minInterval = TreeConfig.TimeInterVal;
        long timeSlotCount = Math.floorDiv(timeOffset , minInterval);
        return timeSlotToIndex(timeSlotCount);
    }

    public int[] timeSlotToIndex(long timeSlotCount){
        int[] indexList = new int[TreeConfig.TreeLevel];
        long blockLength= TreeConfig.BlockLength;
        for (int i = TreeConfig.TreeLevel-1; i >=0; i--) {
            int index = (int) (timeSlotCount % blockLength);
            indexList[i] = index;
            timeSlotCount = Math.floorDiv(timeSlotCount , blockLength);
        }
        return indexList;
    }

    public void timeRangeSearch(long startTime, long endTime, TreeNode treeNode){
        long startSlot = timeToSlot(startTime);
        long endSlot = timeToSlot(endTime);
        int[] startIndexList = timeOffsetToIndex(startTime);
        int[] endIndexList = timeOffsetToIndex(endTime);
        //TODO: find by range
    }
    public long timeToSlot(long time){
        long timeOffset = time - TreeConfig.MinimumTime;
        long minInterval = TreeConfig.TimeInterVal;
        return Math.floorDiv(timeOffset , minInterval);
    }
    public void addTimeIntervalByTime(long time, TreeNode rootNode){
        addTimeIntervalBySlotIndex(timeToSlot(time), rootNode);
    }

    public TreeNode addTimeIntervalBySlotIndex(long slotIndex, TreeNode rootNode){
        //TODO:aggregate
        TreeNode pointer = rootNode;
        int[] indexList = timeSlotToIndex(slotIndex);
        for (int i = 0; i < TreeConfig.TreeLevel; i++) {
            String nextNodeId = pointer.getChildNodes()[indexList[i]];
            if(nextNodeId!=null){
                Optional<TreeNode> treeNodeInMongo = treeNodeRepository.findById(nextNodeId);
                if(treeNodeInMongo.isPresent()){
                    pointer = treeNodeInMongo.get();
                    continue;
                }
            }
            TreeNode newNode = addTreeNode(new TreeNode(pointer.getUid()));
            pointer.getChildNodes()[indexList[i]] = newNode.getUid();
            treeNodeRepository.save(pointer);
            pointer = newNode;
        }
        return pointer;
    }
    public TreeNode addTreeNode(TreeNode treeNode){
        return treeNodeRepository.insert(treeNode);
    }
    public TreeNode findNodeById(String id) throws Exception {
        return treeNodeRepository.findById(id).orElseThrow(()->new Exception("can't find!"));
    }

}
