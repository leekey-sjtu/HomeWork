package com.bytedance.homework.homework5;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DictBean {

    private Web_trans web_trans;
    private String input;
    private String le;
    private String lang;

    public void setInput(String input) {
        this.input = input;
    }
    public String getInput() {
        return input;
    }

    public void setLe(String le) {
        this.le = le;
    }
    public String getLe() {
        return le;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
    public String getLang() {
        return lang;
    }

    public void setWeb_trans(Web_trans web_trans) {
        this.web_trans = web_trans;
    }
    public Web_trans getWeb_trans() {
        return web_trans;
    }

    public class Web_trans {
        @SerializedName("web-translation")
        private List<Web_translation> web_translation;
        public void setWeb_translation(List<Web_translation> web_translation) {
            this.web_translation = web_translation;
        }
        public List<Web_translation> getWeb_translation() {
            return web_translation;
        }
    }

    public class Web_translation {
        private String key;
        private List<Trans> trans;
        public void setKey(String key) {
            this.key = key;
        }
        public String getKey() {
            return key;
        }
        public void setTrans(List<Trans> trans) {
            this.trans = trans;
        }
        public List<Trans> getTrans() {
            return trans;
        }
    }

    public class Trans {
        private String value;
        private int support;
        private String url;
        public void setValue(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
        public void setSupport(int support) {
            this.support = support;
        }
        public int getSupport() {
            return support;
        }
        public void setUrl(String url) {
            this.url = url;
        }
        public String getUrl() {
            return url;
        }
    }


//    private Ce ce;
//    private Wuguanghua wuguanghua;
//    private Ce_new ce_new;
//    private Simple simple;
//    private Media_sents_part media_sents_part;
//    private Baike baike;
//    private Meta meta;
//    private Newhh newhh;

    private Blng_sents_part blng_sents_part;

    public void setBlng_sents_part(Blng_sents_part blng_sents_part) {
        this.blng_sents_part = blng_sents_part;
    }
    public Blng_sents_part getBlng_sents_part() {
        return blng_sents_part;
    }


    public class Blng_sents_part {
        @SerializedName("sentence-pair")
        private List<Sentence_pair> sentence_pair;
        public void setSentence_pair(List<Sentence_pair> sentence_pair) {
            this.sentence_pair = sentence_pair;
        }
        public List<Sentence_pair> getSentence_pair() {
            return sentence_pair;
        }
    }

    public class Sentence_pair {
        private String sentence;
        @SerializedName("sentence-eng")
        private String sentence_eng;
        @SerializedName("sentence-translation")
        private String sentence_translation;
        public void setSentence(String sentence) {
            this.sentence = sentence;
        }
        public String getSentence() {
            return sentence;
        }
        public void setSentence_eng(String sentence_eng) {
            this.sentence_eng = sentence_eng;
        }
        public String getSentence_eng() {
            return sentence_eng;
        }
        public void setSentence_translation(String sentence_translation) {
            this.sentence_translation = sentence_translation;
        }
        public String getSentence_translation() {
            return sentence_translation;
        }
    }


    private Ec ec;

    public void setEc(Ec ec) {
        this.ec = ec;
    }
    public Ec getEc() {
        return ec;
    }

    public class Ec {

        private List<Word> word;
        public void setWord(List<Word> word) {
            this.word = word;
        }
        public List<Word> getWord() {
            return word;
        }

    }

    public class Word {
        private List<Trs> trs;
        private String usphone;
        private String ukphone;
        public void setTrs(List<Trs> trs) {
            this.trs = trs;
        }
        public List<Trs> getTrs() {
            return trs;
        }
        public void setUsphone(String phone) {
            this.usphone = usphone;
        }
        public String getUsphone() {
            return usphone;
        }
        public void setUkphone(String phone) {
            this.ukphone = ukphone;
        }
        public String getUkphone() {
            return ukphone;
        }
    }

    public class Trs {
        private List<Tr> tr;
        public void setTr(List<Tr> tr) {
            this.tr = tr;
        }
        public List<Tr> getTr() {
            return tr;
        }
    }

    public class Tr {
        private L l;
        public void setL(L l) {
            this.l = l;
        }
        public L getL() {
            return l;
        }
    }

    public class L {

        private List<String> i;
        public void setI(List<String> i) {
            this.i = i;
        }
        public List<String> getI() {
            return i;
        }

    }

//    public class L {
//        private String i;
//        public void setI(String i) {
//            this.i = i;
//        }
//        public String getI() {
//            return i;
//        }
//    }

//    public class Tr {
//        private String nat;
//        private String cite;
//        public void setNat(String nat) {
//            this.nat = nat;
//        }
//        public String getNat() {
//            return nat;
//        }
//        public void setCite(String cite) {
//            this.cite = cite;
//        }
//        public String getCite() {
//            return cite;
//        }
//    }

//    public void setCe(Ce ce) {
//        this.ce = ce;
//    }
//    public Ce getCe() {
//        return ce;
//    }
//
//    public class Ce {
//
//        private Source source;
//        private List<Word> word;
//        public void setSource(Source source) {
//            this.source = source;
//        }
//        public Source getSource() {
//            return source;
//        }
//
//        public void setWord(List<Word> word) {
//            this.word = word;
//        }
//        public List<Word> getWord() {
//            return word;
//        }
//
//    }
//
//    public class Source {
//
//        private String name;
//        public void setName(String name) {
//            this.name = name;
//        }
//        public String getName() {
//            return name;
//        }
//
//    }
//
//    public class Word {
//
//        private String phone;
//        private String return_phrase;
//        public void setPhone(String phone) {
//            this.phone = phone;
//        }
//        public String getPhone() {
//            return phone;
//        }
//
//        public void setReturn_phrase(String return_phrase) {
//            this.return_phrase = return_phrase;
//        }
//        public String getReturn_phrase() {
//            return return_phrase;
//        }
//
//    }
//
//    public void setWuguanghua(Wuguanghua wuguanghua) {
//        this.wuguanghua = wuguanghua;
//    }
//    public Wuguanghua getWuguanghua() {
//        return wuguanghua;
//    }
//
//    public void setCe_new(Ce_new ce_new) {
//        this.ce_new = ce_new;
//    }
//    public Ce_new getCe_new() {
//        return ce_new;
//    }
//
//    public void setSimple(Simple simple) {
//        this.simple = simple;
//    }
//    public Simple getSimple() {
//        return simple;
//    }
//
//    public void setMedia_sents_part(Media_sents_part media_sents_part) {
//        this.media_sents_part = media_sents_part;
//    }
//    public Media_sents_part getMedia_sents_part() {
//        return media_sents_part;
//    }

//    public void setBaike(Baike baike) {
//        this.baike = baike;
//    }
//    public Baike getBaike() {
//        return baike;
//    }
//
//    public void setMeta(Meta meta) {
//        this.meta = meta;
//    }
//    public Meta getMeta() {
//        return meta;
//    }

//    public void setNewhh(Newhh newhh) {
//        this.newhh = newhh;
//    }
//    public Newhh getNewhh() {
//        return newhh;
//    }

//    @Override
//    public String toString() {
//        return "web_trans=" + web_trans +
//                ", \ninput='" + input + '\'' +
//                ", \nle='" + le + '\'' +
//                ", \nlang='" + lang + '\'';
//    }
}