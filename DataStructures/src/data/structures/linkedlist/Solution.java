package data.structures.linkedlist;

class Solution {

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        ListNode(int[] arr) {
            if(arr == null || arr.length == 0)
                throw new IllegalArgumentException("arr can not be empty");
            this.val = arr[0];
            ListNode prev = this;
            for (int i = 1; i < arr.length; i++) {
                prev.next = new ListNode(arr[i]);
                prev = prev.next;
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("[");
            for(ListNode cur = this;cur != null;cur = cur.next)
                sb.append(cur.val + "->");
            sb.append("NULL]");
            return sb.toString();
        }
    }

    public ListNode removeElements(ListNode head, int val) {
        while (head != null && head.val == val) {
            ListNode delNode = head;
            head = head.next;
            delNode.next = null;
        }
        if (head == null)
            return head;
        for (ListNode prev = head; prev.next != null; ) {
            if (prev.next.val == val) {
                ListNode delNode = prev.next;
                prev.next = delNode.next;
                delNode.next = null;
            } else {
                prev = prev.next;
            }
        }
        return head;
    }

    public ListNode removeElementsSample(ListNode head, int val) {
        while (head != null && head.val == val) {
            head = head.next;
        }
        if (head == null)
            return head;
        for (ListNode prev = head; prev.next != null; ) {
            if (prev.next.val == val)
                prev.next = prev.next.next;
            else
                prev = prev.next;
        }
        return head;
    }

    public ListNode removeElementsWithDummy(ListNode head, int val) {
        if(head == null)
            return null;
        ListNode dummyHead = new ListNode(-1);
        dummyHead.next = head;
        for (ListNode prev = dummyHead; prev.next != null; ) {
            if (prev.next.val == val)
                prev.next = prev.next.next;
            else
                prev = prev.next;
        }
        return dummyHead.next;
    }

    /*public ListNode removeElementsRec(ListNode head, int val){
        if(head == null)
            return null;
        if(head.val == val) {
            ListNode delNode = head;
            head = head.next;
            delNode.next = null;
            head = removeElementsRec(head, val);
        }else{
            ListNode body = head.next;
            body = removeElementsRec(body, val);
            head.next = body;
        }
        return head;
    }*/

    public ListNode removeElementsRec(ListNode head, int val){
        /*if(head == null)
            return null;
        if(head.val == val)
            head = removeElementsRec(head.next, val);
        else
            head.next = removeElementsRec(head.next, val);
        return head;*/
        if(head == null)
            return null;
        head.next = removeElements(head.next, val);
        return head.val == val?head.next:head;
    }

    public static void main(String[] args) {

        int[] nums = {6, 6, 1, 2, 6, 3, 4, 6, 5, 6, 6, 6};
        Solution so = new Solution();
        ListNode head = so.new ListNode(nums);
        System.out.println(head);

        //ListNode res = so.removeElements(head, 6);
        //ListNode res = so.removeElementsSample(head, 6);
        //ListNode res = so.removeElementsWithDummy(head, 6);
        ListNode res = so.removeElementsRec(head, 6);
        System.out.println(res);
    }

}