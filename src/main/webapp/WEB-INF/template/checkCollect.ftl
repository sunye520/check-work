<?xml version="1.0"?>
<?mso-application progid="Excel.Sheet"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
          xmlns:o="urn:schemas-microsoft-com:office:office"
          xmlns:x="urn:schemas-microsoft-com:office:excel"
          xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
          xmlns:html="http://www.w3.org/TR/REC-html40">
    <DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
        <Author>ZhangBoshu</Author>
        <LastAuthor>ZhangBoshu</LastAuthor>
        <Created>2018-12-19T07:57:05Z</Created>
        <LastSaved>2018-12-20T07:10:30Z</LastSaved>
        <Version>16.00</Version>
    </DocumentProperties>
    <OfficeDocumentSettings xmlns="urn:schemas-microsoft-com:office:office">
        <AllowPNG/>
    </OfficeDocumentSettings>
    <ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
        <WindowHeight>7920</WindowHeight>
        <WindowWidth>21570</WindowWidth>
        <WindowTopX>32767</WindowTopX>
        <WindowTopY>32767</WindowTopY>
        <ProtectStructure>False</ProtectStructure>
        <ProtectWindows>False</ProtectWindows>
    </ExcelWorkbook>
    <Styles>
        <Style ss:ID="Default" ss:Name="Normal">
            <Alignment ss:Vertical="Center"/>
                                            <Borders/>
                                                     <Font ss:FontName="等线" x:CharSet="134" ss:Size="11" ss:Color="#000000"/>
                                                                                                                            <Interior/>
                                                                                                                                      <NumberFormat/>
                                                                                                                                                    <Protection/>
        </Style>
        <Style ss:ID="s16">
            <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
        </Style>
        <Style ss:ID="s20">
            <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
                                                                   <NumberFormat ss:Format="yyyy&quot;年&quot;m&quot;月&quot;"/>
        </Style>
        <Style ss:ID="s22">
            <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
                                                                   <Font ss:FontName="等线" x:CharSet="134" ss:Size="20" ss:Color="#FF0000"/>
        </Style>
        <Style ss:ID="s69">
            <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
                                                                   <Font ss:FontName="等线" x:CharSet="134" ss:Size="11" ss:Color="#FF0000"/>
        </Style>
    </Styles>
    <Worksheet ss:Name="Sheet1">
        <Table ss:ExpandedColumnCount="31" ss:ExpandedRowCount="${resultList?size*2+3}" x:FullColumns="1"
               x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="14.25">
            <Column ss:AutoFitWidth="0" ss:Width="87.75"/>
            <Column ss:StyleID="s16" ss:AutoFitWidth="0" ss:Width="87.75"/>
            <Column ss:AutoFitWidth="0" ss:Width="87.75" ss:Span="19"/>
            <Row ss:Height="25.5">
                <Cell ss:MergeAcross="21" ss:StyleID="s22"><Data ss:Type="String">优格员工考勤汇总表</Data></Cell>
            </Row>
            <Row ss:AutoFitHeight="0">
                <Cell ss:StyleID="s69">
                    <Data ss:Type="String">考勤时间</Data>
                </Cell>
                <Cell ss:StyleID="s20">
                    <Data ss:Type="String">${punchDate}</Data>
                </Cell>
                <Cell ss:StyleID="s16"/>
                <Cell ss:StyleID="s16"/>
                <Cell ss:StyleID="s16"/>
                <Cell ss:StyleID="s16"/>
                <Cell ss:StyleID="s16"/>
                <Cell ss:StyleID="s16"/>
                <Cell ss:StyleID="s16"/>
                <Cell ss:StyleID="s16"/>
                <Cell ss:StyleID="s16"/>
                <Cell ss:StyleID="s16"/>
                <Cell ss:StyleID="s16"/>
                <Cell ss:StyleID="s16"/>
                <Cell ss:StyleID="s16"/>
                <Cell ss:StyleID="s16"/>
                <Cell ss:StyleID="s16"/>
                <Cell ss:StyleID="s16"/>
                <Cell ss:StyleID="s16"/>
                <Cell ss:StyleID="s16"/>
                <Cell ss:StyleID="s16"/>
                <Cell ss:StyleID="s16"/>
            </Row>
            <#if labelList?? >
                <#list labelList as labelBean>
            <Row ss:StyleID="s16">
                <Cell>
                    <Data ss:Type="String">${labelBean.xuhao?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.number?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.chi0?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.chi10?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.chi20?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.chi30?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.zao0?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.zao10?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.zao20?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.zao30?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.key0?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.key1?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.key2?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.key3?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.key4?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.key5?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.key6?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.key7?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.key8?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.key9?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.key10?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.key11?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.key12?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.key13?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.key14?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.key15?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.key16?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.key17?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.key18?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.key19?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${labelBean.key20?if_exists}</Data>
                </Cell>
            </Row>
                </#list>
            </#if>
            <#if resultList?? >
            <#list resultList as resultBean>
            <Row>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.xuhao?if_exists}</Data>
                </Cell>
                <Cell>
                    <Data ss:Type="String">${resultBean.name?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.chi0?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.chi10?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.chi20?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.chi30?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.zao0?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.zao10?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.zao20?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.zao30?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.key0?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.key1?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.key2?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.key3?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.key4?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.key5?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.key6?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.key7?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.key8?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.key9?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.key10?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.key11?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.key12?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.key13?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.key14?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.key15?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.key16?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.key17?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.key18?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.key19?if_exists}</Data>
                </Cell>
                <Cell ss:MergeDown="1" ss:StyleID="s16">
                    <Data ss:Type="String">${resultBean.key20?if_exists}</Data>
                </Cell>
            </Row>
            <Row>
                <Cell ss:Index="2">
                    <Data ss:Type="String">${resultBean.number?if_exists}</Data>
                </Cell>
            </Row>
            </#list>
            </#if>
        </Table>
        <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
            <PageSetup>
                <Header x:Margin="0.3"/>
                <Footer x:Margin="0.3"/>
                <PageMargins x:Bottom="0.75" x:Left="0.7" x:Right="0.7" x:Top="0.75"/>
            </PageSetup>
            <Print>
                <ValidPrinterInfo/>
                <PaperSizeIndex>9</PaperSizeIndex>
                <HorizontalResolution>600</HorizontalResolution>
                <VerticalResolution>600</VerticalResolution>
            </Print>
            <Selected/>
            <Panes>
                <Pane>
                    <Number>3</Number>
                    <ActiveRow>7</ActiveRow>
                    <ActiveCol>3</ActiveCol>
                </Pane>
            </Panes>
            <ProtectObjects>False</ProtectObjects>
            <ProtectScenarios>False</ProtectScenarios>
        </WorksheetOptions>
    </Worksheet>
</Workbook>
