package com.superspork.api;

public class EsSearchResult
{
    public int getTook() {
        return took;
    }

    public void setTook(int took) {
        this.took = took;
    }

    public boolean isTimed_out() {
        return timed_out;
    }

    public void setTimed_out(boolean timed_out) {
        this.timed_out = timed_out;
    }

    public EsSearchResultHits getHits() {
        return hits;
    }

    public void setHits(EsSearchResultHits hits) {
        this.hits = hits;
    }

    private int took;
    private boolean timed_out;
    private EsSearchResultHits hits;

    public class EsSearchResultHits
    {
        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public EsUserGetWrapper[] getHits() {
            return hits;
        }

        public void setHits(EsUserGetWrapper[] hits) {
            this.hits = hits;
        }

        private int total;
        private EsUserGetWrapper[] hits;
    }

}



