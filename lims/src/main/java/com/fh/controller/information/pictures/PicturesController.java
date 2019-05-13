package com.fh.controller.information.pictures;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.DateUtil;
import com.fh.util.DelAllFile;
import com.fh.util.FileUpload;
import com.fh.util.GetWeb;
import com.fh.util.Jurisdiction;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.Tools;
import com.fh.util.Watermark;
import com.fh.service.information.pictures.PicturesManager;

/** 
 * @className：图片管理
 * @author ：FH Q313596790
 * @date：2015-03-21
 */
@Controller
@RequestMapping(value="/pictures")
public class PicturesController extends BaseController {
	/**
	 * @param menuUrl  菜单地址(权限用)
	 */
	String menuUrl = "pictures/list.do";
	@Resource(name="picturesService")
	private PicturesManager picturesService;
	
	/**列表
	 * @param page
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 检索条件
		String keyword = pd.getString("keyword");
		if(null != keyword && !"".equals(keyword)){
			pd.put("KEYW", keyword.trim());
		}
		page.setPd(pd);
		// 列出Pictures列表
		List<PageData>	varList = picturesService.list(page);
		mv.setViewName("information/pictures/pictures_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		// 按钮权限
		mv.addObject("QX", Jurisdiction.getHC());
		return mv;
	}
	
	/**列表(用于弹窗)
	 * @param page
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/listfortc")
	public ModelAndView listfortc(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 检索条件
		String keyword = pd.getString("keyword");
		if(null != keyword && !"".equals(keyword)){
			pd.put("KEYW", keyword.trim());
		}
		page.setPd(pd);
		// 列出Pictures列表
		List<PageData>	varList = picturesService.list(page);
		mv.setViewName("information/pictures/pictures_list_tc");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		// 按钮权限
		mv.addObject("QX", Jurisdiction.getHC());
		return mv;
	}
	
	/**新增
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save(
			@RequestParam(required=false) MultipartFile file
			) throws Exception{
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){
			return null;
		}
		logBefore(logger, Jurisdiction.getUsername()+"新增图片");
		Map<String, String> map = new HashMap<String, String>(16);
		String  ffile = DateUtil.getDays(), fileName = "";
		PageData pd = new PageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){
			if (null != file && !file.isEmpty()) {
				// 文件上传路径
				String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile;
				// 执行上传
				fileName = FileUpload.fileUp(file, filePath, this.get32UUID());
			}else{
				System.out.println("上传失败");
			}
			// 主键
			pd.put("PICTURES_ID", this.get32UUID());
			// 标题
			pd.put("TITLE", "图片");
			// 文件名
			pd.put("NAME", fileName);
			// 路径
			pd.put("PATH", ffile + "/" + fileName);
			// 创建时间
			pd.put("CREATETIME", Tools.date2Str(new Date()));
			// 附属与
			pd.put("MASTER_ID", "1");
			// 备注
			pd.put("BZ", "图片管理处上传");
			// 加水印
			Watermark.setWatemark(PathUtil.getClasspath() + Const.FILEPATHIMG + ffile + "/" + fileName);
			picturesService.save(pd);
		}
		map.put("result", "ok");
		return AppUtil.returnObject(pd, map);
	}
	
	/**删除
	 * @param out
	 * @throws Exception 
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;}
		logBefore(logger, Jurisdiction.getUsername()+"删除图片");
		PageData pd = new PageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			pd = this.getPageData();
			if(Tools.notEmpty(pd.getString("PATH").trim())){
				// 删除图片
				DelAllFile.delFolder(PathUtil.getClasspath()+ Const.FILEPATHIMG + pd.getString("PATH"));
			}
			picturesService.delete(pd);
		}
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param request
	 * @param file
	 * @param tpz
	 * @param pictures_id	// 图片ID
	 * @param title // 标题
	 * @param master_id 	// 属于ID
	 * @param bz  // 备注
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit(
			HttpServletRequest request,
			@RequestParam(value="tp", required=false) MultipartFile file,
			@RequestParam(value="tpz", required=false) String tpz,
			@RequestParam(value="PICTURES_ID", required=false) String pictures_id,
			@RequestParam(value="TITLE", required=false) String title,
			@RequestParam(value="MASTER_ID", required=false) String master_id,
			@RequestParam(value="BZ", required=false) String bz
			) throws Exception{
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		logBefore(logger, Jurisdiction.getUsername()+"修改图片");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			pd.put("PICTURES_ID", pictures_id);
			pd.put("TITLE", title);
			pd.put("MASTER_ID", master_id);
			pd.put("BZ", bz);
			if(null == tpz){
				tpz = "";
			}
			String  ffile = DateUtil.getDays();
			String fileName = "";
			String path = ffile + "/" + fileName;
			if (null != file && !file.isEmpty()) {
				// 文件上传路径
				String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile;
				// 执行上传
				fileName = FileUpload.fileUp(file, filePath, this.get32UUID());
				// 路径
				pd.put("PATH", path);
				pd.put("NAME", fileName);
				//加水印
				Watermark.setWatemark(PathUtil.getClasspath() + Const.FILEPATHIMG + path);
			}else{
				pd.put("PATH", tpz);
			}
			// 执行修改数据库
			picturesService.edit(pd);
		}
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**去新增页面
	 * @return
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("information/pictures/pictures_add");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**去修改页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 根据ID读取
		pd = picturesService.findById(pd);
		mv.setViewName("information/pictures/pictures_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**批量删除
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception {
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>(16);
		pd = this.getPageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			List<PageData> pdList = new ArrayList<PageData>();
			List<PageData> pathList = new ArrayList<PageData>();
			String data_ids = pd.getString("DATA_IDS");
			if(null != data_ids && !"".equals(data_ids)){
				String[] arrayData_ids = data_ids.split(",");
				pathList = picturesService.getAllById(arrayData_ids);
				for(int i = 0; i<pathList.size(); i++){
					if(Tools.notEmpty(pathList.get(i).getString("PATH").trim())){
						// 删除图片
						DelAllFile.delFolder(PathUtil.getClasspath()+ Const.FILEPATHIMG +
								pathList.get(i).getString("PATH"));
					}
				}
				picturesService.deleteAll(arrayData_ids);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
			}
		return AppUtil.returnObject(pd, map);
	}
	
	/**删除图片
	 * @param out
	 * @throws Exception 
	 */
	@RequestMapping(value="/deltp")
	public void deltp(PrintWriter out) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		String PATH = pd.getString("PATH");
		// 图片路径
		if(Tools.notEmpty(pd.getString("PATH").trim())){
			// 删除图片
			DelAllFile.delFolder(PathUtil.getClasspath()+ Const.FILEPATHIMG + pd.getString("PATH"));
		}
		if(PATH != null){
			//删除数据库中图片数据
			picturesService.delTp(pd);
		}	
		out.write("success");
		out.close();
	}
	
	/**去图片爬虫页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goImageCrawler")
	public ModelAndView goImageCrawler() throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("information/pictures/imageCrawler");
		return mv;
	}
	
	/**
	 *	请求连接获取网页中每个图片的地址
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/getImagePath")
	@ResponseBody
	public Object getImagePath(){
		Map<String, Object> map = new HashMap<String, Object>(16);
		PageData pd = new PageData();
		pd = this.getPageData();
		List<String> imgList = new ArrayList<String>();
		String errInfo = "success";
		// 网页地址
		String serverUrl = pd.getString("serverUrl");
		// msg:save 时保存到服务器
		String msg = pd.getString("msg");
		// 检验地址是否http://
		if (!serverUrl.startsWith("http://")){
			// 无效地址
			 errInfo = "error";
		 }else{
			 try {
				imgList = GetWeb.getImagePathList(serverUrl);
				if("save".equals(msg)){
					String ffile = DateUtil.getDays();
					// 文件上传路径
					String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile;
					// 把网络图片保存到服务器硬盘，并数据库记录
					for(int i=0;i<imgList.size();i++){
						/**下载网络图片上传到服务器上,保存到数据库
						 * @param fileName    文件名
						 * @param path         文件路径
						 * @param PICTURES_ID  // 主键
						 * @param TITLE        // 标题
						 * @param NAME         // 文件名
						 * @param  PATH        // 路径
						 * @param  CREATETIME  // 创建时间
						 * @param  MASTER_ID   // 附属与
						 * @param  BZ          // 备注
						 */
						String fileName = FileUpload.getHtmlPicture(imgList.get(i), filePath,null);
						String path = ffile + "/" + fileName;
						pd.put("PICTURES_ID", this.get32UUID());
						pd.put("TITLE", "图片");
						pd.put("NAME", fileName);
						pd.put("PATH", path);
						pd.put("CREATETIME", Tools.date2Str(new Date()));
						pd.put("MASTER_ID", "1");
						pd.put("BZ", serverUrl+"爬取");
						//加水印
						Watermark.setWatemark(PathUtil.getClasspath() + Const.FILEPATHIMG +path );
						picturesService.save(pd);
					}
				}
			} catch (Exception e) {
				 // 出错
				errInfo = "error";
			}
		}
		// 图片集合
		map.put("imgList", imgList);
		// 返回结果
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
