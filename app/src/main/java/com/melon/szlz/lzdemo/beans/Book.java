package com.melon.szlz.lzdemo.beans;

import java.util.List;

/**
 * 日期：2018/4/10
 * 作者：melon
 * 邮箱：960275325@qq.com
 * ----------------我是好人------------------
 */
public class Book {

    /**
     * book : bookname
     * baseUri : lzjy/audios
     * pageNumbers : 3
     * pages : [{"page":0,"readings":[{"reading":"952.wav"},{"reading":"953.wav"}],"playcount":2,"questions":[{"question":[{"section":"333.wav","answer":{"x":2,"y":1}}]}],"coordinate":[{"x":1,"y":2,"uri":"430.wav"},{"x":3,"y":1,"uri":"431.wav"}]},{"page":1,"readings":[{"reading":"956.wav"},{"reading":"957.wav"},{"reading":"958.wav"}],"playcount":2,"questions":[{"question":[{"section":"443.wav","answer":{"x":2,"y":1}}]}],"coordinate":[{"x":1,"y":1,"uri":"521.wav"},{"x":2,"y":2,"uri":"522.wav"}]},{"page":2,"readings":[{"reading":"961.wav"},{"reading":"962.wav"},{"reading":"963.wav"}],"playcount":2,"questions":[{"question":[{"section":"543.wav","answer":{"x":2,"y":1}}]}],"coordinate":[{"x":1,"y":1,"uri":"444.wav"},{"x":2,"y":2,"uri":"445.wav"}]}]
     */

    private String book;
    private String baseUri;
    private int pageNumbers;
    private List<PagesBean> pages;

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    public int getPageNumbers() {
        return pageNumbers;
    }

    public void setPageNumbers(int pageNumbers) {
        this.pageNumbers = pageNumbers;
    }

    public List<PagesBean> getPages() {
        return pages;
    }

    public void setPages(List<PagesBean> pages) {
        this.pages = pages;
    }

    public static class PagesBean {
        /**
         * page : 0
         * readings : [{"reading":"952.wav"},{"reading":"953.wav"}]
         * playcount : 2
         * questions : [{"question":[{"section":"333.wav","answer":{"x":2,"y":1}}]}]
         * coordinate : [{"x":1,"y":2,"uri":"430.wav"},{"x":3,"y":1,"uri":"431.wav"}]
         */

        private int page;
        private int playcount;
        private List<ReadingsBean> readings;
        private List<QuestionsBean> questions;
        private List<CoordinateBean> coordinate;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPlaycount() {
            return playcount;
        }

        public void setPlaycount(int playcount) {
            this.playcount = playcount;
        }

        public List<ReadingsBean> getReadings() {
            return readings;
        }

        public void setReadings(List<ReadingsBean> readings) {
            this.readings = readings;
        }

        public List<QuestionsBean> getQuestions() {
            return questions;
        }

        public void setQuestions(List<QuestionsBean> questions) {
            this.questions = questions;
        }

        public List<CoordinateBean> getCoordinate() {
            return coordinate;
        }

        public void setCoordinate(List<CoordinateBean> coordinate) {
            this.coordinate = coordinate;
        }

        public static class ReadingsBean {
            /**
             * reading : 952.wav
             */

            private String reading;

            public String getReading() {
                return reading;
            }

            public void setReading(String reading) {
                this.reading = reading;
            }
        }

        public static class QuestionsBean {
            private List<QuestionBean> question;

            public List<QuestionBean> getQuestion() {
                return question;
            }

            public void setQuestion(List<QuestionBean> question) {
                this.question = question;
            }

            public static class QuestionBean {
                /**
                 * section : 333.wav
                 * answer : {"x":2,"y":1}
                 */

                private String section;
                private AnswerBean answer;

                public String getSection() {
                    return section;
                }

                public void setSection(String section) {
                    this.section = section;
                }

                public AnswerBean getAnswer() {
                    return answer;
                }

                public void setAnswer(AnswerBean answer) {
                    this.answer = answer;
                }

                public static class AnswerBean {
                    /**
                     * x : 2
                     * y : 1
                     */

                    private int x;
                    private int y;

                    public int getX() {
                        return x;
                    }

                    public void setX(int x) {
                        this.x = x;
                    }

                    public int getY() {
                        return y;
                    }

                    public void setY(int y) {
                        this.y = y;
                    }
                }
            }
        }

        public static class CoordinateBean {
            /**
             * x : 1
             * y : 2
             * uri : 430.wav
             */

            private int x;
            private int y;
            private String uri;

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            public String getUri() {
                return uri;
            }

            public void setUri(String uri) {
                this.uri = uri;
            }
        }
    }
}
