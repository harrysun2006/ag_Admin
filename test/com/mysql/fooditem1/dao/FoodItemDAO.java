package com.mysql.fooditem1.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mysql.fooditem1.po.HFoodItem;
import com.mysql.util.Hibernate3Helper;
import com.sophia.pagination.Hibernate3Counter;
import com.sophia.pagination.Page;

public class FoodItemDAO 
{

    public FoodItemDAO() 
    {
    }

    public int addFoodItem(HFoodItem foodItem, Session session)
        throws Exception
    {
        int result = 0;
        Transaction tx = null;
        boolean isSessionFromOuter = true;
        try
        {
            if(session == null)
            {
                session = Hibernate3Helper.currentSession();
                tx = session.beginTransaction();
                isSessionFromOuter = false;
            }
            session.save(foodItem);
            if(!isSessionFromOuter && tx != null)
                tx.commit();
            result = 1;
        }
        catch(Exception e)
        {
            try
            {
                if(!isSessionFromOuter && tx != null)
                    tx.rollback();
            }
            catch(Exception exception) { }
            throw e;
        }
        finally
        {
            try
            {
                if(!isSessionFromOuter)
                    Hibernate3Helper.closeSession();
            }
            catch(Exception ex) { }
        }
        return result;
    }

    public int updateFoodItem(HFoodItem foodItem, Session session)
        throws Exception
    {
        int result = 0;
        Transaction tx = null;
        boolean isSessionFromOuter = true;
        try
        {
            if(session == null)
            {
                session = Hibernate3Helper.currentSession();
                tx = session.beginTransaction();
                isSessionFromOuter = false;
            }
            HFoodItem temp = new HFoodItem();
            temp = (HFoodItem)session.get(HFoodItem.class, foodItem.getId());
            if(temp != null)
            {
            	temp.setName(foodItem.getName());
            	temp.setPoints(foodItem.getPoints());
                session.update(temp);
                result = 1;
            }
            if(!isSessionFromOuter && tx != null)
                tx.commit();
        }
        catch(Exception e)
        {
            try
            {
                if(!isSessionFromOuter && tx != null)
                    tx.rollback();
            }
            catch(Exception exception) { }
            throw e;
        }
        finally
        {
            try
            {
                if(!isSessionFromOuter)
                    Hibernate3Helper.closeSession();
            }
            catch(Exception ex) { }
        }
        return result;
    }

    public int deleteFoodItem(HFoodItem foodItem, Session session)
        throws Exception
    {
        int result = 0;
        Transaction tx = null;
        boolean isSessionFromOuter = true;
        try
        {
            if(session == null)
            {
                session = Hibernate3Helper.currentSession();
                tx = session.beginTransaction();
                isSessionFromOuter = false;
            }
            if(foodItem != null)
            {
                session.delete(foodItem);
                result = 1;
            }
            if(!isSessionFromOuter && tx != null)
                tx.commit();
        }
        catch(Exception e)
        {
            try
            {
                if(!isSessionFromOuter && tx != null)
                    tx.rollback();
            }
            catch(Exception exception) { }
            throw e;
        }
        finally
        {
            try
            {
                if(!isSessionFromOuter)
                    Hibernate3Helper.closeSession();
            }
            catch(Exception ex) { }
        }
        return result;
    }

    public HFoodItem getFoodItem(Long id, Session session)
        throws Exception
    {
        HFoodItem foodItem = null;
        boolean isSessionFromOuter = true;
        try
        {
            if(session == null)
            {
                session = Hibernate3Helper.currentSession();
                isSessionFromOuter = false;
            }
            foodItem = (HFoodItem)session.get(HFoodItem.class, id);
        }
        catch(Exception e)
        {
            throw e;
        }
        finally
        {
            try
            {
                if(!isSessionFromOuter)
                    Hibernate3Helper.closeSession();
            }
            catch(Exception ex) { }
        }
        return foodItem;
    }

    public void loadFoodItem(HFoodItem foodItem, Session session)
        throws Exception
    {
        boolean isSessionFromOuter = true;
        try
        {
            if(session == null)
            {
                session = Hibernate3Helper.currentSession();
                isSessionFromOuter = false;
            }
            session.load(foodItem, foodItem.getId());
        }
        catch(Exception e)
        {
            throw e;
        }
        finally
        {
            try
            {
                if(!isSessionFromOuter)
                    Hibernate3Helper.closeSession();
            }
            catch(Exception ex) { }
        }
    }

    public List getFoodItemList(HFoodItem foodItem, Page page, Session session)
        throws Exception
    {
        Query query = null;
        List list = new ArrayList();
        StringBuffer hql = new StringBuffer();
        boolean isSessionFromOuter = true;
        try
        {
            hql.append("select fi from HFoodItem fi where 1 = 1");
            if(foodItem != null)
            {
                if(foodItem.getName() != null && foodItem.getName().trim().length() > 0)
                    hql.append(" and fi.name like :name");
            }
            if(session == null)
            {
                session = Hibernate3Helper.currentSession();
                isSessionFromOuter = false;
            }
            query = session.createQuery(hql.toString());
            if(foodItem != null)
            {
            	if(foodItem.getName() != null && foodItem.getName().trim().length() > 0)
                    query.setParameter("name", "%" + foodItem.getName() + "%");
            }
            if(page != null && page != null)
                page.paginate(new Hibernate3Counter(query, session));
            list = query.list();
        }
        catch(Exception e)
        {
            throw e;
        }
        finally
        {
            try
            {
                if(!isSessionFromOuter)
                    Hibernate3Helper.closeSession();
            }
            catch(Exception ex) { }
        }
        return list;
    }
}