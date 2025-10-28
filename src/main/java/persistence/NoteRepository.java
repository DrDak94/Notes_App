package persistence;

import entity.Note;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

public class NoteRepository {

    public void create(Note note){
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(note);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Note> findAll(){
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT n FROM Note n");
        List<Note> notes = query.getResultList();
        em.close();
        return notes;
    }

    public Note findById(int id){
        EntityManager em = JPAUtil.getEntityManager();
        Note note = null;
        try {
            note = em.find(Note.class, id);
        } finally {
            em.close();
        }
        return note;
    }

    public void update(Note note) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(note);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void delete(int id){
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Note note = em.find(Note.class, id);
            if (note != null){
                em.remove(note);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
