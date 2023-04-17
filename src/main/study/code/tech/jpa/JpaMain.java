package code.tech.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            logic(em);
            tx.commit();
        } catch(Exception ex){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void logic(EntityManager em) {
        String id = "id1";
        MemberHello member = new MemberHello();
        member.setId(id);
        member.setUsername("Jsing");
        member.setAge(30);

        em.persist(member);

        member.setAge(20);

        MemberHello findMember = em.find(MemberHello.class, id);
        System.out.println(findMember);

        List<MemberHello> members = em.createQuery("select m from MEMBER_HELLO m", MemberHello.class).getResultList();
        System.out.println(members);

        em.remove(member);
    }
}
