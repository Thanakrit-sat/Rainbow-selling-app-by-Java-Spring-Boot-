package ku.cs.shop.models;

import ku.cs.shop.services.filterer.ConditionFilterer;

import java.util.ArrayList;

public class CommentList {
    private ArrayList<Commentator> commentList;

    public CommentList() {
        commentList = new ArrayList<>();
    }

    public void addComment(Commentator commentator) {
        commentList.add(commentator);
    }

    public int countComments() { return commentList.size(); }

    public void setCommentList(ArrayList<Commentator> commentList) {
        this.commentList = commentList;
    }

    public int getAmountByProductName(String productName) {
        int amount = 0;
        for (Commentator commentator : commentList) {
            if (commentator.getToProductName().equals(productName)) {
                amount += 1;
            }
        }
        return amount;
    }

    public double getReviewScoreByProductName(String productName) {
        double score = 0;
        int count = 0;
        for (Commentator commentator : commentList) {
            if (commentator.getToProductName().equals(productName)) {
                score += commentator.getScore();
                count += 1;
            }
        }
        if (score == 0 || count == 0) {
            return 0;
        }
        return score/count;
    }

    public Commentator getCommentByIndex(int index){ return commentList.get(index); }

    public String toCSV() {
        String result = "";
        for (Commentator commentator : commentList) {
            result += commentator.toCSV() + "\n";
        }
        return result;
    }

    public ArrayList<Commentator> search(ConditionFilterer<Commentator> filterer) {
        ArrayList<Commentator> filtered = new ArrayList<>();

        for (Commentator commentator : this.commentList) {
            if (filterer.match(commentator)) {
                filtered.add(commentator);
            }
        }

        return filtered;
    }
}
