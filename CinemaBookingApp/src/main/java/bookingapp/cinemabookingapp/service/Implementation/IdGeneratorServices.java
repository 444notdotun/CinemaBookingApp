package bookingapp.cinemabookingapp.service.Implementation;

import bookingapp.cinemabookingapp.data.models.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class IdGeneratorServices {
   @Autowired
   MongoTemplate mongoTemplate;


   public String generateId(String entityName) {
            Query query = new Query(Criteria.where("_id").is(entityName));
            Update update = new Update().inc("seq", 1);
            Counter counter = mongoTemplate.findAndModify(
                    query,
                    update,
                    FindAndModifyOptions.options().returnNew(true).upsert(true),
                    Counter.class
            );
            return entityName + (counter != null ? counter.getSeq() : 1);
   }
}
