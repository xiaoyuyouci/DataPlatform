<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<script type="text/javascript" src="res/custom/scheduleJob/updScheduleJob.js"></script>
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="row">
					<div class="col-lg-12">
						<form role="form" id="updScheduleJobForm" onSubmit="return false;">
							<div class="form-group input-group">
								<input type="hidden" id="id" name="id" value="${obj.id }">
                                <span class="input-group-addon">任务组*</span>
                            	<input type="text" id="jobGroup" name="jobGroup" value="${obj.jobGroup }" readonly class="form-control" placeholder="请输入任务组">
                            </div>
                            <div class="form-group input-group">
                                <span class="input-group-addon">任务名*</span>
                            	<input type="text" id="jobName" name="jobName" value="${obj.jobName }" readonly class="form-control" placeholder="请输入任务名">
                            </div>
							<div class="form-group input-group">
								<span class="input-group-addon">描述</span> 
								<input type="text" id="description" name="description" value="${obj.description }" class="form-control" placeholder="请输入任务描述">
							</div>
							<div class="form-group input-group">
								<span class="input-group-addon">是否允许同时运行</span> 
								<select class="form-control" name="isConcurrent">
                                	<option value="0" ${obj.isConcurrent == '0'? 'selected':'' }>否</option>
                                	<option value="1" ${obj.isConcurrent == '1'? 'selected':'' }>是</option>
                            	</select>
							</div>
							<div class="form-group input-group">
								<span class="input-group-addon">cron表达式*</span> 
								<input type="text" id="cronExpression" name="cronExpression" value="${obj.cronExpression }" class="form-control" placeholder="请输入cron表达式">
							</div>
							<div class="form-group input-group">
								<span class="input-group-addon">SpringId</span> 
								<input type="text" id="springId" name="springId" value="${obj.springId }" class="form-control" placeholder="请输入SpringId">
							</div>
							<div class="form-group input-group">
								<span class="input-group-addon">类名</span> 
								<input type="text" id="beanClass" name="beanClass" value="${obj.beanClass }" class="form-control" placeholder="请输入完整类名">
							</div>
							<div class="form-group input-group">
								<span class="input-group-addon">方法名*</span>
								<input type="text" id="methodName" name="methodName" value="${obj.methodName }" class="form-control" placeholder="请输入方法名">
							</div>
							<button type="submit" class="btn btn-default">提交</button>
							<button type="reset" class="btn btn-default">重置</button>
						</form>
					</div>
					<!-- /.col-lg-12 (nested) -->
				</div>
				<!-- /.row (nested) -->
			</div>
			<!-- /.panel-body -->
		</div>
		<!-- /.panel -->
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- Page-Level Demo Scripts - Tables - Use for reference -->
