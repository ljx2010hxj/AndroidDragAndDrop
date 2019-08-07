# AndroidDragAndDrop
This project is to demonstrate how to drag and drop view in a ViewGroup or between ViewGroups.

这个demo演示了android中的拖曳或者说拖放操作，可以用来对ViewGroup里面的view进行排序等操作。

## 单个ViewGroup里面进行拖放操作
采用`RecyclerView` + `ItemTouchHelper`实现。主要是实现`ItemTouchHelper`里面的相关接口。

## 跨ViewGroup进行拖放操作
借助Android系统自带拖放操作框架进行。