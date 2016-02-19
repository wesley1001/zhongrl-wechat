package cn.xn.freamwork.model;

import cn.xn.freamwork.util.DateUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 分页查询基类
 * @author lcl 2014/07/10
 * @version 1.0.0
 */
public class QueryPage<T> extends RowBounds
{
    //分页条件参数KEY
    public static final String PARAMS_EXAMPLE   = "example";   
    private static final int DEFAULT_PAGE_SIZE  = 10;
    private static final int DEFAULT_CUR_PAGE   = 1;

    //每页记录数
    private Integer pageSize = DEFAULT_PAGE_SIZE;
    //总记录数
    private Integer total    = 0;
    //总页数
    private Integer pageNum  = 0;
    //当前页
    private Integer currentPage = DEFAULT_CUR_PAGE;

    //数据库返回DO数据
    private List<T> data;
    //返回给前端解析JSON数据对象
    private Object rows;
    //条件参数
    private Map<String, Object> params;


    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 时期
     */
    private String period;


    /**
     * init
     */
    public QueryPage()
    {
        super(0, DEFAULT_PAGE_SIZE);
        this.data = null;
        this.rows = null;
        this.params = new HashMap<String, Object>();
    }

    /**
     * init current page, pageSize default 10.
     * @param curPage
     */
    public QueryPage(int curPage)
    {
        //init limit
        super((curPage - 1) * DEFAULT_PAGE_SIZE, DEFAULT_PAGE_SIZE);

        this.currentPage = curPage;
        this.data = null;
        this.params = new HashMap<String, Object>();
    }

    /**
     * init params {size, page}
     * @param pageSize
     * @param curPage
     */
    public QueryPage(int pageSize, int curPage)
    {
        //init limit
        super((curPage - 1) * pageSize, pageSize);

        this.pageSize = pageSize;
        this.currentPage = curPage;
        this.params = new HashMap<String, Object>();
        this.data = null;
    }

    /**
     * init QueryPage Object
     * @param currentPage
     * @param pageSize
     * @return
     */
    public static QueryPage initQueryPage(String currentPage, Object pageSize) {
        QueryPage queryPage = null;
        if (StringUtils.isEmpty(currentPage)) {
            queryPage = new QueryPage();
        } else {
            //分页参数补全
            queryPage = new QueryPage(
                    StringUtils.isEmpty(pageSize) ? DEFAULT_PAGE_SIZE:Integer.valueOf(pageSize.toString()),
                    StringUtils.isEmpty(currentPage) ? DEFAULT_CUR_PAGE:Integer.valueOf(currentPage.toString())
            );
        }

        return queryPage;
    }

    /**
     * 计算总页数
     * @param total
     * @return
     */
    public QueryPage<T> init(int total)
    {
        this.total = total;
        this.pageNum = (this.total % this.pageSize) > 0 ? (this.total / this.pageSize + 1) : (this.total / this.pageSize);

        //避免请求页,大于实际页码
        if (this.currentPage > this.pageNum)
            this.currentPage = this.pageNum;

        return this;
    }


    /**
     * 添加查询参数
     * @param key
     * @param value
     * @return
     */
    public QueryPage<T> addParams(String key, Object value) {
        this.params.put(key, value);
        return this;
    }

    /**
     * 是否需要查询数据库(过滤前端输入请求页码超出实际页)
     * @return
     */
    public boolean selectFlag() {
        return (this.total <= 0 || this.currentPage > this.pageNum) ? false:true;
    }


    public Integer getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }

    public Integer getTotal()
    {
        return total;
    }

    public void setTotal(Integer total)
    {
        this.total = total;
    }

    public Integer getPageNum()
    {
        return pageNum;
    }

    public void setPageNum(Integer pageNum)
    {
        this.pageNum = pageNum;
    }

    public Integer getCurrentPage()
    {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage)
    {
        this.currentPage = currentPage;
    }


    public Map<String, Object> getParams()
    {
        return params;
    }

    public void setParams(Map<String, Object> params)
    {
        this.params = params;
    }

    public List<T> getData()
    {
        return data;
    }

    public void setData(List<T> data)
    {
        this.data = data;
    }

    public Object getRows()
    {
        return rows;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public String getPeriod()
    {
        return period;
    }

    public void setPeriod(String period)
    {
        this.period = period;
    }

    public void setRows(Object data)
    {
        if (null != data) {
            this.rows = data;
            this.data = null;
            this.params = null;
        }
    }

    public void setRows(Object data, boolean clearFlag)
    {
        if (null != data) {
            this.rows = data;
            if (clearFlag) {
                this.data = null;
                this.params = null;
            }
        }
    }

    /**
     * 分期查询参数初始化
     */
    public void periodToDate() {

        if(getPeriod()==null||"".equals(getPeriod().trim())){
            return ;
        }
        if ("customDate".equals(getPeriod()))
        {
            return;
        }
        if("today".equals(getPeriod())){
            Date now=new Date();
            setStartDate(now);
            setEndDate(now);

        } else {
            Calendar curr = Calendar.getInstance();
            setEndDate(curr.getTime());
            if("lastWeek".equals(getPeriod())){
                curr.add(Calendar.DAY_OF_YEAR,-7);
            }else if("lastMonth".equals(getPeriod())){
                curr.add(Calendar.DAY_OF_YEAR,-30);
            }else if("last3Month".equals(getPeriod())){
                curr.add(Calendar.DAY_OF_YEAR,-90);
            }

            setStartDate( DateUtils.format(
                    DateUtils.format(curr.getTime(), DateUtils.FORMAT_DATE) + " 00:00:00"
                    , DateUtils.FORMAT_TIME) );
        }
    }


}
