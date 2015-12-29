package org.spider.corpus.tools.subject;

import java.util.ArrayList;
import java.util.List;

public class Subject {

    private List<String> subject; // 主语
    private List<String> predicate; // 谓语
    private List<String> object; // 宾语

    public List<String> getSubject() {
        return subject;
    }

    public void addSubject(String _subject) {
        if (subject == null) {
            subject = new ArrayList<>();
        }
        
        subject.add(_subject);
    }

    public List<String> getPredicate() {
        return predicate;
    }

    public void addPredicate(String _predicate) {
        if (predicate == null) {
            predicate = new ArrayList<>();
        }
        
        predicate.add(_predicate);
    }

    public List<String> getObject() {
        return object;
    }

    public void addObject(String _object) {
        if (object == null) {
            object = new ArrayList<>();
        }
        
        object.add(_object);
    }
    
    public List<List<String>> getMembers() {
        List<List<String>> members = new ArrayList<>();
        members.add(getSubject());
        members.add(getPredicate());
        members.add(getObject());
        
        return members;
    }

    @Override
    public String toString() {
        return "{" + getSubject() + ", " + getPredicate() + ", " + getObject() + "}";
    }
}
