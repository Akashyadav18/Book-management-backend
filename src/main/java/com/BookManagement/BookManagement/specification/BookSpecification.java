package com.BookManagement.BookManagement.specification;

import com.BookManagement.BookManagement.entity.Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BookSpecification {
    public static Specification<Book> getSpecification(String search){
//        normal specification without is deleted false

//        return new Specification<Book>() {
//            @Override
//            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                //root- Here we pass entity i.e(book), any thing related to book(col, fields) we will fetch from root.
//                //Query- this is D query that we are trying to build.
//                //criteriaBuilder- all the condition, that we want to give in our query, that we build by using criteriaBuilder
//
//                // Select * from Book where title= :search or author= :search or category= :search
//                if(search == null || search.isEmpty()){
//                    return criteriaBuilder.conjunction(); //don't apply any filter.
//                }
//
//                List<Predicate> list = new ArrayList<>();
//
////                equal → Exact/Strict match(category, year) | like → Partial match (Search ke liye- title, author)
////                problem - exact match. DB-Angular = User-Angular ✅ | Angular = angular ❌
//                list.add(criteriaBuilder.equal(root.get("category"), search));
////                DB- angular | user- angular. (Dono values lower ho gai)
//                list.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("author")), search.toLowerCase()));
////                DB-Angular = User-angul ✅
//                list.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%"+search.toLowerCase() + "%"));
//
//
////      Sab conditions ko OR se join karo    List → Array convert karo
//                return criteriaBuilder.or(list.toArray(new Predicate[0]));
//            }
//        };

//        specification with is deleted false

//        search = "angular" diya:
//        predicates = [
//        isDeleted = false,                              ← hamesha
//                (title LIKE '%angular%' OR author LIKE '%angular%')  ← search wala
//]
//        Final: WHERE isDeleted = false AND (title LIKE '%angular%' OR author LIKE '%angular%')
//
//        search = null diya:
//        predicates = [
//        isDeleted = false   ← sirf yahi
//]
//        Final: WHERE isDeleted = false

        return new Specification<Book>() {

            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                if(search == null || search.isEmpty()){
                    return cb.conjunction(); //don't apply any filter.
                }

                List<Predicate> predicates = new ArrayList<>();

                // Condition 1: hamesha isDeleted = false lagao
                predicates.add(cb.equal(root.get("isDeleted"), false));

                // Condition 2: search null nahi hai toh filter lagao
                if (search != null && !search.trim().isEmpty()) {

                    List<Predicate> searchPredicates = new ArrayList<>();

                    searchPredicates.add(cb.like(cb.lower(root.get("title")), "%" + search.toLowerCase() + "%"));
                    searchPredicates.add(cb.like(cb.lower(root.get("author")), "%" + search.toLowerCase() + "%"));

                    // title OR author — kisi ek mein mile toh chalega
                    predicates.add(cb.or(searchPredicates.toArray(new Predicate[0])));
                }

                // sab conditions AND se join karo
                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}

