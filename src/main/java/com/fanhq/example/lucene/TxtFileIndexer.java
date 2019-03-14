package com.fanhq.example.lucene;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by Hachel on 2018/11/5
 */
public class TxtFileIndexer {
    public static void main(String[] args) throws Exception {
        // 存放在内存中
        Directory dir = new SimpleFSDirectory(Paths.get("D:\\file\\luceneIndex"));
        createIndex(dir);
        search(dir);
    }

    /**
     * 创建索引
     */
    public static void createIndex(Directory dir) throws IOException {
        IndexWriter indexWriter = null;
        // 1. 创建 Directory (索引存放位置)，这里是参数dir

        // 2. 创建IndexWriter 写索引
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        indexWriter = new IndexWriter(dir, iwc);

        // 3. 创建Document 对象 field
        Document document;
        File file = new File("D:\\file\\oneNet");
        for (File f : file.listFiles()) {
            document = new Document();

            // 4. 为Documen添加field
            document.add(new Field("content", FileUtils.readFileToString(f, "utf-8"), TextField.TYPE_STORED));
            document.add(new TextField("fileName", f.getName(), Field.Store.YES));
            document.add(new StringField("filePath", f.getAbsolutePath(), Field.Store.YES));
            // 5. 通过IndexWriter 添加文档到索引中
            indexWriter.addDocument(document);
        }
        indexWriter.close();
    }

    /**
     * 根据内容搜索
     *
     * @param dir
     * @throws Exception
     */
    public static void search(Directory dir) throws Exception {
        IndexReader indexReader = null;
        // 1. 创建 Directory，这里通过参数传递过来的dir
        // Directory dir = FSDirectory.open(new File("file/index").toPath()); // 本地磁盘

        // 2. 创建 IndexReader
        indexReader = DirectoryReader.open(dir);

        // 3. 创建 IndexSearch
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        // 4. 创建搜索的Query
        // 创建parse确定搜索的内容，第二个参数为搜索的file
        QueryParser queryParser = new QueryParser("content", new StandardAnalyzer());

        // 创建Query，表示搜索域中的内容
        Query query = queryParser.parse("TCP");

        // 5. 搜索并返回 TopDocs
        TopDocs topDocs = indexSearcher.search(query, 10);

        // 6. 根据topDocs 获得 scoreDocs
        ScoreDoc[] socreDocs = topDocs.scoreDocs;

        for (ScoreDoc doc : socreDocs) {
            // 获取Document对象
            Document document = indexSearcher.doc(doc.doc);

            // 根据Document对象获取需要的值
            System.out.println(document.get("fileName"));
            System.out.println(document.get("content"));
        }
        indexReader.close();
    }
}
