/*
 * Anserini: A Lucene toolkit for reproducible information retrieval research
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.anserini.eval;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;

public class RelevanceJudgmentsTest{

  public int getQrelsCount(RelevanceJudgments qrels) throws IOException{
    int count = 0;
    for (String qid : qrels.getQids()) {
      count += qrels.getDocMap(qid).size();
    }
    return count;
  }

  @Test
  public void testTotalCount() {
    assertEquals(184, Qrels.values().length);
  }

  @Test(expected = IOException.class)
  public void testFileNotFound() throws IOException{
    // Purposely read non-existent file.
    new RelevanceJudgments("tools/topics-and-qrels/qrels.xxx.txt");
  }

  @Test(expected = IOException.class)
  public void testNonvalidQrels() throws IOException{
    // Purposely read non-valid qrels.
    new RelevanceJudgments("tools/topics-and-qrels/topics.robust04.txt ");
  }

  @Test
  public void testRobust04() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.robust04.txt");
    assertNotNull(qrels);
    assertEquals(249, qrels.getQids().size());
    assertEquals(311410, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("301", "FBIS3-10082"));
    assertEquals(0, qrels.getRelevanceGrade("700", "LA123090-0137"));
    assertEquals(0, qrels.getRelevanceGrade("700", "LA123090-0137x")); // non-existent docid
    assertEquals(0, qrels.getRelevanceGrade("xxx", "LA123090-0137"));  // non-existent topic
    assertTrue(qrels.isDocJudged("301", "FBIS3-10082"));
    assertNull(qrels.getDocMap("xxx"));

    qrels = RelevanceJudgments.fromQrels(Qrels.ROBUST04);
    assertNotNull(qrels);
    assertEquals(249, qrels.getQids().size());
    assertEquals(311410, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("301", "FBIS3-10082"));
    assertEquals(0, qrels.getRelevanceGrade("700", "LA123090-0137"));
    assertEquals(0, qrels.getRelevanceGrade("700", "LA123090-0137x")); // non-existent docid
    assertEquals(0, qrels.getRelevanceGrade("xxx", "LA123090-0137"));  // non-existent topic
    assertTrue(qrels.isDocJudged("301", "FBIS3-10082"));
    assertNull(qrels.getDocMap("xxx"));

    assertEquals(6543541, RelevanceJudgments.getQrelsResource(Path.of("tools/topics-and-qrels/qrels.robust04.txt")).length());
  }

  @Test
  public void testRobust05() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.robust05.txt");
    assertNotNull(qrels);
    assertEquals(50, qrels.getQids().size());
    assertEquals(37798, getQrelsCount(qrels));
    assertEquals(2, qrels.getRelevanceGrade("303", "APW19980609.1531"));
    assertEquals(0, qrels.getRelevanceGrade("689", "XIE20000925.0055"));

    qrels = RelevanceJudgments.fromQrels(Qrels.ROBUST05);
    assertNotNull(qrels);
    assertEquals(50, qrels.getQids().size());
    assertEquals(37798, getQrelsCount(qrels));
    assertEquals(2, qrels.getRelevanceGrade("303", "APW19980609.1531"));
    assertEquals(0, qrels.getRelevanceGrade("689", "XIE20000925.0055"));
  }

  @Test
  public void testTrec19DLDoc() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.dl19-doc.txt");
    assertNotNull(qrels);
    assertEquals(43, qrels.getQids().size());
    assertEquals(16258, getQrelsCount(qrels));
    assertEquals(0, qrels.getRelevanceGrade("19335", "D1035833"));
    assertEquals(0, qrels.getRelevanceGrade("1133167", "D984590"));

    qrels = RelevanceJudgments.fromQrels(Qrels.TREC2019_DL_DOC);
    assertNotNull(qrels);
    assertEquals(43, qrels.getQids().size());
    assertEquals(16258, getQrelsCount(qrels));
    assertEquals(0, qrels.getRelevanceGrade("19335", "D1035833"));
    assertEquals(0, qrels.getRelevanceGrade("1133167", "D984590"));
  }

  @Test
  public void testTrec19DLPassage() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.dl19-passage.txt");
    assertNotNull(qrels);
    assertEquals(43, qrels.getQids().size());
    assertEquals(9260, getQrelsCount(qrels));
    assertEquals(0, qrels.getRelevanceGrade("19335", "1017759"));
    assertEquals(1, qrels.getRelevanceGrade("1133167", "8804478"));

    qrels = RelevanceJudgments.fromQrels(Qrels.TREC2019_DL_PASSAGE);
    assertNotNull(qrels);
    assertEquals(43, qrels.getQids().size());
    assertEquals(9260, getQrelsCount(qrels));
    assertEquals(0, qrels.getRelevanceGrade("19335", "1017759"));
    assertEquals(1, qrels.getRelevanceGrade("1133167", "8804478"));
  }

  @Test
  public void testTrec20DLDoc() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.dl20-doc.txt");
    assertNotNull(qrels);
    assertEquals(45, qrels.getQids().size());
    assertEquals(9098, getQrelsCount(qrels));
    assertEquals(3, qrels.getRelevanceGrade("42255", "D1884223"));
    assertEquals(3, qrels.getRelevanceGrade("1136962", "D96741"));

    qrels = RelevanceJudgments.fromQrels(Qrels.TREC2020_DL_DOC);
    assertNotNull(qrels);
    assertEquals(45, qrels.getQids().size());
    assertEquals(9098, getQrelsCount(qrels));
    assertEquals(3, qrels.getRelevanceGrade("42255", "D1884223"));
    assertEquals(3, qrels.getRelevanceGrade("1136962", "D96741"));
  }

  @Test
  public void testTrec20DLPassage() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.dl20-passage.txt");
    assertNotNull(qrels);
    assertEquals(54, qrels.getQids().size());
    assertEquals(11386, getQrelsCount(qrels));
    assertEquals(2, qrels.getRelevanceGrade("23849", "1020327"));
    assertEquals(1, qrels.getRelevanceGrade("1136962", "937258"));

    qrels = RelevanceJudgments.fromQrels(Qrels.TREC2020_DL_PASSAGE);
    assertNotNull(qrels);
    assertEquals(54, qrels.getQids().size());
    assertEquals(11386, getQrelsCount(qrels));
    assertEquals(2, qrels.getRelevanceGrade("23849", "1020327"));
    assertEquals(1, qrels.getRelevanceGrade("1136962", "937258"));
  }

  @Test
  public void testTrec21DLDoc() throws IOException{
    // % cut -f 1 -d ' ' tools/topics-and-qrels/qrels.dl21-doc.txt | uniq | wc
    //       57      57     412
    // % wc tools/topics-and-qrels/qrels.dl21-doc.txt
    //    13058   52232  478328 tools/topics-and-qrels/qrels.dl21-doc.txt

    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.dl21-doc.txt");
    assertNotNull(qrels);
    assertEquals(57, qrels.getQids().size());
    assertEquals(13058, getQrelsCount(qrels));
    assertEquals(2, qrels.getRelevanceGrade("2082", "msmarco_doc_01_1320020407"));
    assertEquals(1, qrels.getRelevanceGrade("1129560", "msmarco_doc_59_863449044"));

    qrels = RelevanceJudgments.fromQrels(Qrels.TREC2021_DL_DOC);
    assertNotNull(qrels);
    assertEquals(57, qrels.getQids().size());
    assertEquals(13058, getQrelsCount(qrels));
    assertEquals(2, qrels.getRelevanceGrade("2082", "msmarco_doc_01_1320020407"));
    assertEquals(1, qrels.getRelevanceGrade("1129560", "msmarco_doc_59_863449044"));
  }

  @Test
  public void testTrec21DLPassage() throws IOException{
    // % cut -f 1 -d ' ' tools/topics-and-qrels/qrels.dl21-passage.txt | uniq | wc
    //       53      53     382
    // % wc tools/topics-and-qrels/qrels.dl21-passage.txt
    //    10828   43312  433887 tools/topics-and-qrels/qrels.dl21-passage.txt

    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.dl21-passage.txt");
    assertNotNull(qrels);
    assertEquals(53, qrels.getQids().size());
    assertEquals(10828, getQrelsCount(qrels));
    assertEquals(3, qrels.getRelevanceGrade("2082", "msmarco_passage_02_179207466"));
    assertEquals(1, qrels.getRelevanceGrade("1129560", "msmarco_passage_67_937656589"));

    qrels = RelevanceJudgments.fromQrels(Qrels.TREC2021_DL_PASSAGE);
    assertNotNull(qrels);
    assertEquals(53, qrels.getQids().size());
    assertEquals(10828, getQrelsCount(qrels));
    assertEquals(3, qrels.getRelevanceGrade("2082", "msmarco_passage_02_179207466"));
    assertEquals(1, qrels.getRelevanceGrade("1129560", "msmarco_passage_67_937656589"));
  }

  @Test
  public void testTrec21DLDocMsMarcoV21() throws IOException{
    // % cut -f 1 -d ' ' tools/topics-and-qrels/qrels.dl21-doc-msmarco-v2.1.txt | sort | uniq | wc
    //       57      57     412
    // % wc tools/topics-and-qrels/qrels.dl21-doc-msmarco-v2.1.txt
    //    10973   43892  456277 tools/topics-and-qrels/qrels.dl21-doc-msmarco-v2.1.txt

    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.dl21-doc-msmarco-v2.1.txt");
    assertNotNull(qrels);
    assertEquals(57, qrels.getQids().size());
    assertEquals(10973, getQrelsCount(qrels));
    assertEquals(2, qrels.getRelevanceGrade("2082", "msmarco_v2.1_doc_01_1281570012"));
    assertEquals(2, qrels.getRelevanceGrade("1128632", "msmarco_v2.1_doc_17_481617788"));

    qrels = RelevanceJudgments.fromQrels(Qrels.TREC2021_DL_DOC_MSMARCO_V21);
    assertNotNull(qrels);
    assertEquals(57, qrels.getQids().size());
    assertEquals(10973, getQrelsCount(qrels));
    assertEquals(2, qrels.getRelevanceGrade("2082", "msmarco_v2.1_doc_01_1281570012"));
    assertEquals(2, qrels.getRelevanceGrade("1128632", "msmarco_v2.1_doc_17_481617788"));
  }

  @Test
  public void testTrec22DLDoc() throws IOException{
    // % cut -f 1 -d ' ' tools/topics-and-qrels/qrels.dl22-doc.txt | uniq | wc
    //       76      76     608
    // % wc tools/topics-and-qrels/qrels.dl22-doc.txt
    //   369638 1478552 13808681 tools/topics-and-qrels/qrels.dl22-doc.txt

    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.dl22-doc.txt");
    assertNotNull(qrels);
    assertEquals(76, qrels.getQids().size());
    assertEquals(369638, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("2000511", "msmarco_doc_00_928744703"));
    assertEquals(1, qrels.getRelevanceGrade("2056323", "msmarco_doc_59_419476385"));

    qrels = RelevanceJudgments.fromQrels(Qrels.TREC2022_DL_DOC);
    assertNotNull(qrels);
    assertEquals(76, qrels.getQids().size());
    assertEquals(369638, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("2000511", "msmarco_doc_00_928744703"));
    assertEquals(1, qrels.getRelevanceGrade("2056323", "msmarco_doc_59_419476385"));
  }

  @Test
  public void testTrec22DLPassage() throws IOException{
    // % cut -f 1 -d ' ' tools/topics-and-qrels/qrels.dl22-passage.txt | uniq | wc
    //      76      76     608
    // % wc tools/topics-and-qrels/qrels.dl22-passage.txt
    //  386416 1545664 15800539 tools/topics-and-qrels/qrels.dl22-passage.txt

    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.dl22-passage.txt");
    assertNotNull(qrels);
    assertEquals(76, qrels.getQids().size());
    assertEquals(386416, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("2000511", "msmarco_passage_00_491585864"));
    assertEquals(1, qrels.getRelevanceGrade("2056323", "msmarco_passage_68_715747739"));

    qrels = RelevanceJudgments.fromQrels(Qrels.TREC2022_DL_PASSAGE);
    assertNotNull(qrels);
    assertEquals(76, qrels.getQids().size());
    assertEquals(386416, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("2000511", "msmarco_passage_00_491585864"));
    assertEquals(1, qrels.getRelevanceGrade("2056323", "msmarco_passage_68_715747739"));
  }

  @Test
  public void testTrec22DLDocMsMarcoV21() throws IOException{
    // % cut -f 1 -d ' ' tools/topics-and-qrels/qrels.dl22-doc-msmarco-v2.1.txt | sort | uniq | wc
    //       76      76     608
    // % wc tools/topics-and-qrels/qrels.dl22-doc-msmarco-v2.1.txt
    //   349541 1398164 14786970 tools/topics-and-qrels/qrels.dl22-doc-msmarco-v2.1.txt

    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.dl22-doc-msmarco-v2.1.txt");
    assertNotNull(qrels);
    assertEquals(76, qrels.getQids().size());
    assertEquals(349541, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("2000511", "msmarco_v2.1_doc_00_896525856"));
    assertEquals(2, qrels.getRelevanceGrade("2056158", "msmarco_v2.1_doc_06_934688453"));

    qrels = RelevanceJudgments.fromQrels(Qrels.TREC2022_DL_DOC_MSMARCO_V21);
    assertNotNull(qrels);
    assertEquals(76, qrels.getQids().size());
    assertEquals(349541, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("2000511", "msmarco_v2.1_doc_00_896525856"));
    assertEquals(2, qrels.getRelevanceGrade("2056158", "msmarco_v2.1_doc_06_934688453"));
  }

  @Test
  public void testTrec23DLDoc() throws IOException{
    // % cut -f 1 -d ' ' tools/topics-and-qrels/qrels.dl23-doc.txt | uniq | wc
    //       82      82     656
    // % wc tools/topics-and-qrels/qrels.dl23-doc.txt
    //    18034   72136  675015 tools/topics-and-qrels/qrels.dl23-doc.txt

    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.dl23-doc.txt");
    assertNotNull(qrels);
    assertEquals(82, qrels.getQids().size());
    assertEquals(18034, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("2001010", "msmarco_doc_00_1413652624"));
    assertEquals(3, qrels.getRelevanceGrade("3100922", "msmarco_doc_16_3928760942"));

    qrels = RelevanceJudgments.fromQrels(Qrels.TREC2023_DL_DOC);
    assertNotNull(qrels);
    assertEquals(82, qrels.getQids().size());
    assertEquals(18034, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("2001010", "msmarco_doc_00_1413652624"));
    assertEquals(3, qrels.getRelevanceGrade("3100922", "msmarco_doc_16_3928760942"));
  }

  @Test
  public void testTrec23DLPassage() throws IOException{
    // % cut -f 1 -d ' ' tools/topics-and-qrels/qrels.dl23-passage.txt | uniq | wc
    //      82      82     656
    // % wc tools/topics-and-qrels/qrels.dl23-passage.txt
    //   22327   89308  912450 tools/topics-and-qrels/qrels.dl23-passage.txt

    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.dl23-passage.txt");
    assertNotNull(qrels);
    assertEquals(82, qrels.getQids().size());
    assertEquals(22327, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("2001010", "msmarco_passage_00_729315698"));
    assertEquals(2, qrels.getRelevanceGrade("3100922", "msmarco_passage_22_487548813"));

    qrels = RelevanceJudgments.fromQrels(Qrels.TREC2023_DL_PASSAGE);
    assertNotNull(qrels);
    assertEquals(82, qrels.getQids().size());
    assertEquals(22327, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("2001010", "msmarco_passage_00_729315698"));
    assertEquals(2, qrels.getRelevanceGrade("3100922", "msmarco_passage_22_487548813"));
  }

  @Test
  public void testTrec23DLDocMsMarcoV21() throws IOException{
    // % cut -f 1 -d ' ' tools/topics-and-qrels/qrels.dl23-doc-msmarco-v2.1.txt | uniq | wc
    //       82      82     656
    // % wc tools/topics-and-qrels/qrels.dl23-doc-msmarco-v2.1.txt
    //    15995   63980  677618 tools/topics-and-qrels/qrels.dl23-doc-msmarco-v2.1.txt

    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.dl23-doc-msmarco-v2.1.txt");
    assertNotNull(qrels);
    assertEquals(82, qrels.getQids().size());
    assertEquals(15995, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("2001010", "msmarco_v2.1_doc_00_1372241967"));
    assertEquals(2, qrels.getRelevanceGrade("3100922", "msmarco_v2.1_doc_19_1982402861"));

    qrels = RelevanceJudgments.fromQrels(Qrels.TREC2023_DL_DOC_MSMARCO_V21);
    assertNotNull(qrels);
    assertEquals(82, qrels.getQids().size());
    assertEquals(15995, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("2001010", "msmarco_v2.1_doc_00_1372241967"));
    assertEquals(2, qrels.getRelevanceGrade("3100922", "msmarco_v2.1_doc_19_1982402861"));
  }

  @Test
  public void testTREC24_RAG_RAGGY_DEV() throws IOException{
    // % cut -f 1 -d ' ' tools/topics-and-qrels/qrels.rag24.raggy-dev.txt | uniq | wc
    //      120     120     937
    // % wc tools/topics-and-qrels/qrels.rag24.raggy-dev.txt
    //   147328  589312 6377570 tools/topics-and-qrels/qrels.rag24.raggy-dev.txt

    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.rag24.raggy-dev.txt");
    assertNotNull(qrels);
    assertEquals(120, qrels.getQids().size());
    assertEquals(147328, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("2001010", "msmarco_v2.1_doc_00_1372241967"));
    assertEquals(1, qrels.getRelevanceGrade("253263", "msmarco_v2.1_doc_46_843492186"));

    qrels = RelevanceJudgments.fromQrels(Qrels.TREC2024_RAG_RAGGY_DEV);
    assertNotNull(qrels);
    assertEquals(120, qrels.getQids().size());
    assertEquals(147328, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("2001010", "msmarco_v2.1_doc_00_1372241967"));
    assertEquals(1, qrels.getRelevanceGrade("253263", "msmarco_v2.1_doc_46_843492186"));
  }

  @Test
  public void testTREC24_RAG_UMBRELA() throws IOException{
    // % cut -f 1 -d ' ' tools/topics-and-qrels/qrels.rag24.test-umbrela-all.txt | uniq | wc
    //      301     301    3448
    // % wc tools/topics-and-qrels/qrels.rag24.test-umbrela-all.txt
    //   108479  433916 6475451 tools/topics-and-qrels/qrels.rag24.test-umbrela-all.txt

    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.rag24.test-umbrela-all.txt");
    assertNotNull(qrels);
    assertEquals(301, qrels.getQids().size());
    assertEquals(108479, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("2024-145979", "msmarco_v2.1_doc_25_771726319#13_1477564195"));
    assertEquals(1, qrels.getRelevanceGrade("2024-216592", "msmarco_v2.1_doc_52_1092442741#3_2165187686"));

    qrels = RelevanceJudgments.fromQrels(Qrels.TREC2024_RAG_UMBRELA);
    assertNotNull(qrels);
    assertEquals(301, qrels.getQids().size());
    assertEquals(108479, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("2024-145979", "msmarco_v2.1_doc_25_771726319#13_1477564195"));
    assertEquals(1, qrels.getRelevanceGrade("2024-216592", "msmarco_v2.1_doc_52_1092442741#3_2165187686"));
  }

  @Test
  public void testTREC24_RAG() throws IOException{
    // % cut -f 1 -d ' ' tools/topics-and-qrels/qrels.rag24.test.txt | uniq | wc
    //       89      89    1028
    // % wc tools/topics-and-qrels/qrels.rag24.test.txt
    //   20429   81716 1201033 tools/topics-and-qrels/qrels.rag24.test.txt

    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.rag24.test.txt");
    assertNotNull(qrels);
    assertEquals(89, qrels.getQids().size());
    assertEquals(20429, getQrelsCount(qrels));
    assertEquals(0, qrels.getRelevanceGrade("2024-145979", "msmarco_v2.1_doc_00_125364462#6_229054655"));
    assertEquals(1, qrels.getRelevanceGrade("2024-96359", "msmarco_v2.1_doc_54_724887112#1_1700994504"));

    qrels = RelevanceJudgments.fromQrels(Qrels.TREC2024_RAG);
    assertNotNull(qrels);
    assertEquals(89, qrels.getQids().size());
    assertEquals(20429, getQrelsCount(qrels));
    assertEquals(0, qrels.getRelevanceGrade("2024-145979", "msmarco_v2.1_doc_00_125364462#6_229054655"));
    assertEquals(1, qrels.getRelevanceGrade("2024-96359", "msmarco_v2.1_doc_54_724887112#1_1700994504"));
  }

  @Test
  public void testMsmarcoDocDev() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.msmarco-doc.dev.txt");
    assertNotNull(qrels);
    assertEquals(5193, qrels.getQids().size());
    assertEquals(5193, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("2", "D1650436"));
    assertEquals(1, qrels.getRelevanceGrade("1102400", "D677570"));

    qrels = RelevanceJudgments.fromQrels(Qrels.MSMARCO_DOC_DEV);
    assertNotNull(qrels);
    assertEquals(5193, qrels.getQids().size());
    assertEquals(5193, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("2", "D1650436"));
    assertEquals(1, qrels.getRelevanceGrade("1102400", "D677570"));
  }

  @Test
  public void testMsmarcoPassageDevSubset() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.msmarco-passage.dev-subset.txt");
    assertNotNull(qrels);
    assertEquals(6980, qrels.getQids().size());
    assertEquals(7437, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("300674", "7067032"));
    assertEquals(1, qrels.getRelevanceGrade("195199", "8009377"));

    qrels = RelevanceJudgments.fromQrels(Qrels.MSMARCO_PASSAGE_DEV_SUBSET);
    assertNotNull(qrels);
    assertEquals(6980, qrels.getQids().size());
    assertEquals(7437, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("300674", "7067032"));
    assertEquals(1, qrels.getRelevanceGrade("195199", "8009377"));
  }

  @Test
  public void testMsmarcoV2DocDevMsMarcoV21() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.msmarco-v2.1-doc.dev.txt");
    assertNotNull(qrels);
    assertEquals(4552, qrels.getQids().size());
    assertEquals(4702, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("1000000", "msmarco_v2.1_doc_17_1968189952"));
    assertEquals(1, qrels.getRelevanceGrade("999897", "msmarco_v2.1_doc_46_191673440"));

    qrels = RelevanceJudgments.fromQrels(Qrels.MSMARCO_V21_DOC_DEV);
    assertNotNull(qrels);
    assertEquals(4552, qrels.getQids().size());
    assertEquals(4702, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("1000000", "msmarco_v2.1_doc_17_1968189952"));
    assertEquals(1, qrels.getRelevanceGrade("999897", "msmarco_v2.1_doc_46_191673440"));
  }

  @Test
  public void testMsmarcoV2DocDev2MsMarcoV21() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.msmarco-v2.1-doc.dev2.txt");
    assertNotNull(qrels);
    assertEquals(5000, qrels.getQids().size());
    assertEquals(5177, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("1000202", "msmarco_v2.1_doc_08_69146701"));
    assertEquals(1, qrels.getRelevanceGrade("999659", "msmarco_v2.1_doc_08_1247437925"));

    qrels = RelevanceJudgments.fromQrels(Qrels.MSMARCO_V21_DOC_DEV2);
    assertNotNull(qrels);
    assertEquals(5000, qrels.getQids().size());
    assertEquals(5177, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("1000202", "msmarco_v2.1_doc_08_69146701"));
    assertEquals(1, qrels.getRelevanceGrade("999659", "msmarco_v2.1_doc_08_1247437925"));
  }

  @Test
  public void testMsmarcoV2DocPassage() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.msmarco-v2-passage.dev.txt");
    assertNotNull(qrels);
    assertEquals(3903, qrels.getQids().size());
    assertEquals(4009, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("763878", "msmarco_passage_33_459057644"));
    assertEquals(1, qrels.getRelevanceGrade("1091692", "msmarco_passage_23_330102695"));

    qrels = RelevanceJudgments.fromQrels(Qrels.MSMARCO_V2_PASSAGE_DEV);
    assertNotNull(qrels);
    assertEquals(3903, qrels.getQids().size());
    assertEquals(4009, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("763878", "msmarco_passage_33_459057644"));
    assertEquals(1, qrels.getRelevanceGrade("1091692", "msmarco_passage_23_330102695"));
  }

  @Test
  public void testMsmarcoV2DocPassage2() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.msmarco-v2-passage.dev2.txt");
    assertNotNull(qrels);
    assertEquals(4281, qrels.getQids().size());
    assertEquals(4411, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("419507", "msmarco_passage_04_254301507"));
    assertEquals(1, qrels.getRelevanceGrade("961297", "msmarco_passage_18_858458289"));

    qrels = RelevanceJudgments.fromQrels(Qrels.MSMARCO_V2_PASSAGE_DEV2);
    assertNotNull(qrels);
    assertEquals(4281, qrels.getQids().size());
    assertEquals(4411, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("419507", "msmarco_passage_04_254301507"));
    assertEquals(1, qrels.getRelevanceGrade("961297", "msmarco_passage_18_858458289"));
  }

  @Test
  public void testMsmarcoV2DocDev() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.msmarco-v2-doc.dev.txt");
    assertNotNull(qrels);
    assertEquals(4552, qrels.getQids().size());
    assertEquals(4702, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("1000000", "msmarco_doc_17_2560009121"));
    assertEquals(1, qrels.getRelevanceGrade("999942", "msmarco_doc_06_956348348"));

    qrels = RelevanceJudgments.fromQrels(Qrels.MSMARCO_V2_DOC_DEV);
    assertNotNull(qrels);
    assertEquals(4552, qrels.getQids().size());
    assertEquals(4702, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("1000000", "msmarco_doc_17_2560009121"));
    assertEquals(1, qrels.getRelevanceGrade("999942", "msmarco_doc_06_956348348"));
  }

  @Test
  public void testMsmarcoV2DocDev2() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.msmarco-v2-doc.dev2.txt");
    assertNotNull(qrels);
    assertEquals(5000, qrels.getQids().size());
    assertEquals(5178, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("1000202", "msmarco_doc_08_73026062"));
    assertEquals(1, qrels.getRelevanceGrade("999937", "msmarco_doc_05_319743607"));

    qrels = RelevanceJudgments.fromQrels(Qrels.MSMARCO_V2_DOC_DEV2);
    assertNotNull(qrels);
    assertEquals(5000, qrels.getQids().size());
    assertEquals(5178, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("1000202", "msmarco_doc_08_73026062"));
    assertEquals(1, qrels.getRelevanceGrade("999937", "msmarco_doc_05_319743607"));
  }

  @Test
  public void testCore17() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.core17.txt");
    assertNotNull(qrels);
    assertEquals(50, qrels.getQids().size());
    assertEquals(30030, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("307", "1001536"));
    assertEquals(0, qrels.getRelevanceGrade("690", "996059"));

    qrels = RelevanceJudgments.fromQrels(Qrels.CORE17);
    assertNotNull(qrels);
    assertEquals(50, qrels.getQids().size());
    assertEquals(30030, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("307", "1001536"));
    assertEquals(0, qrels.getRelevanceGrade("690", "996059"));
  }

  @Test
  public void testCore18() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.core18.txt");
    assertNotNull(qrels);
    assertEquals(50, qrels.getQids().size());
    assertEquals(26233, getQrelsCount(qrels));
    assertEquals(0, qrels.getRelevanceGrade("321", "004c6120d0aa69da29cc045da0562168"));
    assertEquals(0, qrels.getRelevanceGrade("825", "ff3a25b0-0ba4-11e4-8341-b8072b1e7348"));

    qrels = RelevanceJudgments.fromQrels(Qrels.CORE18);
    assertNotNull(qrels);
    assertEquals(50, qrels.getQids().size());
    assertEquals(26233, getQrelsCount(qrels));
    assertEquals(0, qrels.getRelevanceGrade("321", "004c6120d0aa69da29cc045da0562168"));
    assertEquals(0, qrels.getRelevanceGrade("825", "ff3a25b0-0ba4-11e4-8341-b8072b1e7348"));
  }

  @Test
  public void testCar15() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.car17v1.5.benchmarkY1test.txt");
    assertNotNull(qrels);
    assertEquals(2125, qrels.getQids().size());
    assertEquals(5820, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("Aftertaste/Aftertaste%20processing%20in%20the%20cerebral%20cortex",
        "38c1bd25ddca2705164677a3f598c46df85afba7"));
    assertEquals(1, qrels.getRelevanceGrade("Yellowstone%20National%20Park/Recreation",
        "e80b5185da1493edde41bea19a389a3f62167369"));

    qrels = RelevanceJudgments.fromQrels(Qrels.CAR17V15_BENCHMARK_Y1_TEST);
    assertNotNull(qrels);
    assertEquals(2125, qrels.getQids().size());
    assertEquals(5820, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("Aftertaste/Aftertaste%20processing%20in%20the%20cerebral%20cortex",
            "38c1bd25ddca2705164677a3f598c46df85afba7"));
    assertEquals(1, qrels.getRelevanceGrade("Yellowstone%20National%20Park/Recreation",
            "e80b5185da1493edde41bea19a389a3f62167369"));
  }

  @Test
  public void testCar20() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.car17v2.0.benchmarkY1test.txt");
    assertNotNull(qrels);
    assertEquals(2254, qrels.getQids().size());
    assertEquals(6192, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("enwiki:Aftertaste", "327cca6c4d38953196fa6789f615546f03287b25"));
    assertEquals(1, qrels.getRelevanceGrade("enwiki:Yellowstone%20National%20Park/Recreation",
        "b812fca195f74f8c563db4262260554fe3ff3731"));

    qrels = RelevanceJudgments.fromQrels(Qrels.CAR17V20_BENCHMARK_Y1_TEST);
    assertNotNull(qrels);
    assertEquals(2254, qrels.getQids().size());
    assertEquals(6192, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("enwiki:Aftertaste", "327cca6c4d38953196fa6789f615546f03287b25"));
    assertEquals(1, qrels.getRelevanceGrade("enwiki:Yellowstone%20National%20Park/Recreation",
            "b812fca195f74f8c563db4262260554fe3ff3731"));
  }

  @Test
  public void testTrec2018BL() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.backgroundlinking18.txt");
    assertNotNull(qrels);
    assertEquals(50, qrels.getQids().size());
    assertEquals(8508, getQrelsCount(qrels));
    assertEquals(16, qrels.getRelevanceGrade("321", "00f57310e5c8ec7833d6756ba637332e"));
    assertEquals(0, qrels.getRelevanceGrade("825", "f66b624ba8689d704872fa776fb52860"));

    qrels = RelevanceJudgments.fromQrels(Qrels.TREC2018_BL);
    assertNotNull(qrels);
    assertEquals(50, qrels.getQids().size());
    assertEquals(8508, getQrelsCount(qrels));
    assertEquals(16, qrels.getRelevanceGrade("321", "00f57310e5c8ec7833d6756ba637332e"));
    assertEquals(0, qrels.getRelevanceGrade("825", "f66b624ba8689d704872fa776fb52860"));
  }

  @Test
  public void testTrec2019BL() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.backgroundlinking19.txt");
    assertNotNull(qrels);
    assertEquals(57, qrels.getQids().size());
    assertEquals(15655, getQrelsCount(qrels));
    assertEquals(2, qrels.getRelevanceGrade("826", "0154349511cd8c49ab862d6cb0d8f6a8"));
    assertEquals(0, qrels.getRelevanceGrade("885", "fde80cb0-b4f0-11e2-bbf2-a6f9e9d79e19"));

    qrels = RelevanceJudgments.fromQrels(Qrels.TREC2019_BL);
    assertNotNull(qrels);
    assertEquals(57, qrels.getQids().size());
    assertEquals(15655, getQrelsCount(qrels));
    assertEquals(2, qrels.getRelevanceGrade("826", "0154349511cd8c49ab862d6cb0d8f6a8"));
    assertEquals(0, qrels.getRelevanceGrade("885", "fde80cb0-b4f0-11e2-bbf2-a6f9e9d79e19"));
  }

  @Test
  public void testTrec2020BL() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.backgroundlinking20.txt");
    assertNotNull(qrels);
    assertEquals(49, qrels.getQids().size());
    assertEquals(17764, getQrelsCount(qrels));
    assertEquals(0, qrels.getRelevanceGrade("886", "00183d98-741b-11e5-8248-98e0f5a2e830"));
    assertEquals(0, qrels.getRelevanceGrade("935", "ff0a760128ecdbcc096cafc8cd553255"));

    qrels = RelevanceJudgments.fromQrels(Qrels.TREC2020_BL);
    assertNotNull(qrels);
    assertEquals(49, qrels.getQids().size());
    assertEquals(17764, getQrelsCount(qrels));
    assertEquals(0, qrels.getRelevanceGrade("886", "00183d98-741b-11e5-8248-98e0f5a2e830"));
    assertEquals(0, qrels.getRelevanceGrade("935", "ff0a760128ecdbcc096cafc8cd553255"));
  }

  @Test
  public void testCovidRound1() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.covid-round1.txt");
    assertNotNull(qrels);
    assertEquals(30, qrels.getQids().size());
    assertEquals(8691, getQrelsCount(qrels));
    assertEquals(2, qrels.getRelevanceGrade("1", "010vptx3"));
    assertEquals(1, qrels.getRelevanceGrade("30", "zn87f1lk"));

    qrels = RelevanceJudgments.fromQrels(Qrels.COVID_ROUND1);
    assertNotNull(qrels);
    assertEquals(30, qrels.getQids().size());
    assertEquals(8691, getQrelsCount(qrels));
    assertEquals(2, qrels.getRelevanceGrade("1", "010vptx3"));
    assertEquals(1, qrels.getRelevanceGrade("30", "zn87f1lk"));
  }

  @Test
  public void testCovidRound2() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.covid-round2.txt");
    assertNotNull(qrels);
    assertEquals(35, qrels.getQids().size());
    assertEquals(12037, getQrelsCount(qrels));
    assertEquals(0, qrels.getRelevanceGrade("1", "08efpohc"));
    assertEquals(0, qrels.getRelevanceGrade("35", "zzmfhr2s"));

    qrels = RelevanceJudgments.fromQrels(Qrels.COVID_ROUND2);
    assertNotNull(qrels);
    assertEquals(35, qrels.getQids().size());
    assertEquals(12037, getQrelsCount(qrels));
    assertEquals(0, qrels.getRelevanceGrade("1", "08efpohc"));
    assertEquals(0, qrels.getRelevanceGrade("35", "zzmfhr2s"));
  }

  @Test
  public void testCovidRound3() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.covid-round3.txt");
    assertNotNull(qrels);
    assertEquals(40, qrels.getQids().size());
    assertEquals(12713, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("1", "0194oljo"));
    assertEquals(1, qrels.getRelevanceGrade("40", "zsx7wfyj"));

    qrels = RelevanceJudgments.fromQrels(Qrels.COVID_ROUND3);
    assertNotNull(qrels);
    assertEquals(40, qrels.getQids().size());
    assertEquals(12713, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("1", "0194oljo"));
    assertEquals(1, qrels.getRelevanceGrade("40", "zsx7wfyj"));
  }

  @Test
  public void testCovidRound4() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.covid-round4.txt");
    assertNotNull(qrels);
    assertEquals(45, qrels.getQids().size());
    assertEquals(13262, getQrelsCount(qrels));
    assertEquals(2, qrels.getRelevanceGrade("1", "1c47w4q5"));
    assertEquals(2, qrels.getRelevanceGrade("45", "zzrsk1ls"));

    qrels = RelevanceJudgments.fromQrels(Qrels.COVID_ROUND4);
    assertNotNull(qrels);
    assertEquals(45, qrels.getQids().size());
    assertEquals(13262, getQrelsCount(qrels));
    assertEquals(2, qrels.getRelevanceGrade("1", "1c47w4q5"));
    assertEquals(2, qrels.getRelevanceGrade("45", "zzrsk1ls"));
  }

  @Test
  public void testCovidRound5() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.covid-round5.txt");
    assertNotNull(qrels);
    assertEquals(50, qrels.getQids().size());
    assertEquals(23151, getQrelsCount(qrels));
    assertEquals(2, qrels.getRelevanceGrade("1", "005b2j4b"));
    assertEquals(1, qrels.getRelevanceGrade("50", "zz8wvos9"));

    qrels = RelevanceJudgments.fromQrels(Qrels.COVID_ROUND5);
    assertNotNull(qrels);
    assertEquals(50, qrels.getQids().size());
    assertEquals(23151, getQrelsCount(qrels));
    assertEquals(2, qrels.getRelevanceGrade("1", "005b2j4b"));
    assertEquals(1, qrels.getRelevanceGrade("50", "zz8wvos9"));
  }

  @Test
  public void testCovidRound3Cumulative() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.covid-round3-cumulative.txt");
    assertNotNull(qrels);
    assertEquals(40, qrels.getQids().size());
    assertEquals(33068, getQrelsCount(qrels));
    assertEquals(2, qrels.getRelevanceGrade("1", "010vptx3"));
    assertEquals(1, qrels.getRelevanceGrade("40", "zsx7wfyj"));

    qrels = RelevanceJudgments.fromQrels(Qrels.COVID_ROUND3_CUMULATIVE);
    assertNotNull(qrels);
    assertEquals(40, qrels.getQids().size());
    assertEquals(33068, getQrelsCount(qrels));
    assertEquals(2, qrels.getRelevanceGrade("1", "010vptx3"));
    assertEquals(1, qrels.getRelevanceGrade("40", "zsx7wfyj"));
  }

  @Test
  public void testCovidRound4Cumulative() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.covid-round4-cumulative.txt");
    assertNotNull(qrels);
    assertEquals(45, qrels.getQids().size());
    assertEquals(46203, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("1", "00fmeepz"));
    assertEquals(2, qrels.getRelevanceGrade("45", "zzrsk1ls"));

    qrels = RelevanceJudgments.fromQrels(Qrels.COVID_ROUND4_CUMULATIVE);
    assertNotNull(qrels);
    assertEquals(45, qrels.getQids().size());
    assertEquals(46203, getQrelsCount(qrels));
    assertEquals(1, qrels.getRelevanceGrade("1", "00fmeepz"));
    assertEquals(2, qrels.getRelevanceGrade("45", "zzrsk1ls"));
  }

  @Test
  public void testCovidComplete() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.covid-complete.txt");
    assertNotNull(qrels);
    assertEquals(50, qrels.getQids().size());
    assertEquals(69318, getQrelsCount(qrels));
    assertEquals(2, qrels.getRelevanceGrade("1", "005b2j4b"));
    assertEquals(1, qrels.getRelevanceGrade("50", "zz8wvos9"));

    qrels = RelevanceJudgments.fromQrels(Qrels.COVID_COMPLETE);
    assertNotNull(qrels);
    assertEquals(50, qrels.getQids().size());
    assertEquals(69318, getQrelsCount(qrels));
    assertEquals(2, qrels.getRelevanceGrade("1", "005b2j4b"));
    assertEquals(1, qrels.getRelevanceGrade("50", "zz8wvos9"));
  }

  @Test
  public void testNtcir8Zh() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.ntcir8.eval.txt");
    assertNotNull(qrels);
    assertEquals(100, qrels.getQids().size());
    assertEquals(110213, getQrelsCount(qrels));
    assertEquals(0, qrels.getRelevanceGrade("ACLIA2-CS-0001", "XIN_CMN_20020106.0118"));
    assertEquals(0, qrels.getRelevanceGrade("ACLIA2-CS-0001", "XIN_CMN_20020107.0140"));

    qrels = RelevanceJudgments.fromQrels(Qrels.NTCIR8_ZH);
    assertNotNull(qrels);
    assertEquals(100, qrels.getQids().size());
    assertEquals(110213, getQrelsCount(qrels));
    assertEquals(0, qrels.getRelevanceGrade("ACLIA2-CS-0001", "XIN_CMN_20020106.0118"));
    assertEquals(0, qrels.getRelevanceGrade("ACLIA2-CS-0001", "XIN_CMN_20020107.0140"));
  }

  @Test
  public void testClef2006Fr() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.clef06fr.txt");
    assertNotNull(qrels);
    assertEquals(49, qrels.getQids().size());
    assertEquals(17882, getQrelsCount(qrels));
    assertEquals(0, qrels.getRelevanceGrade("301-AH", "ATS.940106.0082"));
    assertEquals(0, qrels.getRelevanceGrade("301-AH", "ATS.940112.0089"));

    qrels = RelevanceJudgments.fromQrels(Qrels.CLEF2006_FR);
    assertNotNull(qrels);
    assertEquals(49, qrels.getQids().size());
    assertEquals(17882, getQrelsCount(qrels));
    assertEquals(0, qrels.getRelevanceGrade("301-AH", "ATS.940106.0082"));
    assertEquals(0, qrels.getRelevanceGrade("301-AH", "ATS.940112.0089"));
  }

  @Test
  public void testTrec2002Ar() throws IOException{
    RelevanceJudgments qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.trec02ar.txt");
    assertNotNull(qrels);
    assertEquals(50, qrels.getQids().size());
    assertEquals(38432, getQrelsCount(qrels));
    assertEquals(0, qrels.getRelevanceGrade("26", "19940515_AFP_ARB.0115"));
    assertEquals(1, qrels.getRelevanceGrade("26", "19941213_AFP_ARB.0159"));

    qrels = RelevanceJudgments.fromQrels(Qrels.TREC2002_AR);
    assertNotNull(qrels);
    assertEquals(50, qrels.getQids().size());
    assertEquals(38432, getQrelsCount(qrels));
    assertEquals(0, qrels.getRelevanceGrade("26", "19940515_AFP_ARB.0115"));
    assertEquals(1, qrels.getRelevanceGrade("26", "19941213_AFP_ARB.0159"));
  }

  @Test
  public void testMrTyDiAr() throws IOException{
    RelevanceJudgments qrels;

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-ar.train.txt");
    assertNotNull(qrels);
    assertEquals(12377, qrels.getQids().size());
    assertEquals(12377, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_AR_TRAIN);
    assertNotNull(qrels);
    assertEquals(12377, qrels.getQids().size());
    assertEquals(12377, getQrelsCount(qrels));

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-ar.dev.txt");
    assertNotNull(qrels);
    assertEquals(3115, qrels.getQids().size());
    assertEquals(3115, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_AR_DEV);
    assertNotNull(qrels);
    assertEquals(3115, qrels.getQids().size());
    assertEquals(3115, getQrelsCount(qrels));

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-ar.test.txt");
    assertNotNull(qrels);
    assertEquals(1081, qrels.getQids().size());
    assertEquals(1257, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_AR_TEST);
    assertNotNull(qrels);
    assertEquals(1081, qrels.getQids().size());
    assertEquals(1257, getQrelsCount(qrels));
  }

  @Test
  public void testMrTyDiBn() throws IOException{
    RelevanceJudgments qrels;

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-bn.train.txt");
    assertNotNull(qrels);
    assertEquals(1713, qrels.getQids().size());
    assertEquals(1719, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_BN_TRAIN);
    assertNotNull(qrels);
    assertEquals(1713, qrels.getQids().size());
    assertEquals(1719, getQrelsCount(qrels));

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-bn.dev.txt");
    assertNotNull(qrels);
    assertEquals(440, qrels.getQids().size());
    assertEquals(443, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_BN_DEV);
    assertNotNull(qrels);
    assertEquals(440, qrels.getQids().size());
    assertEquals(443, getQrelsCount(qrels));

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-bn.test.txt");
    assertNotNull(qrels);
    assertEquals(111, qrels.getQids().size());
    assertEquals(130, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_BN_TEST);
    assertNotNull(qrels);
    assertEquals(111, qrels.getQids().size());
    assertEquals(130, getQrelsCount(qrels));
  }

  @Test
  public void testMrTyDiEn() throws IOException{
    RelevanceJudgments qrels;

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-en.train.txt");
    assertNotNull(qrels);
    assertEquals(3547, qrels.getQids().size());
    assertEquals(3547, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_EN_TRAIN);
    assertNotNull(qrels);
    assertEquals(3547, qrels.getQids().size());
    assertEquals(3547, getQrelsCount(qrels));

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-en.dev.txt");
    assertNotNull(qrels);
    assertEquals(878, qrels.getQids().size());
    assertEquals(878, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_EN_DEV);
    assertNotNull(qrels);
    assertEquals(878, qrels.getQids().size());
    assertEquals(878, getQrelsCount(qrels));

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-en.test.txt");
    assertNotNull(qrels);
    assertEquals(744, qrels.getQids().size());
    assertEquals(935, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_EN_TEST);
    assertNotNull(qrels);
    assertEquals(744, qrels.getQids().size());
    assertEquals(935, getQrelsCount(qrels));
  }

  @Test
  public void testMrTyDiFi() throws IOException{
    RelevanceJudgments qrels;

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-fi.train.txt");
    assertNotNull(qrels);
    assertEquals(6561, qrels.getQids().size());
    assertEquals(6561, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_FI_TRAIN);
    assertNotNull(qrels);
    assertEquals(6561, qrels.getQids().size());
    assertEquals(6561, getQrelsCount(qrels));

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-fi.dev.txt");
    assertNotNull(qrels);
    assertEquals(1738, qrels.getQids().size());
    assertEquals(1738, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_FI_DEV);
    assertNotNull(qrels);
    assertEquals(1738, qrels.getQids().size());
    assertEquals(1738, getQrelsCount(qrels));

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-fi.test.txt");
    assertNotNull(qrels);
    assertEquals(1254, qrels.getQids().size());
    assertEquals(1451, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_FI_TEST);
    assertNotNull(qrels);
    assertEquals(1254, qrels.getQids().size());
    assertEquals(1451, getQrelsCount(qrels));
  }

  @Test
  public void testMrTyDiId() throws IOException{
    RelevanceJudgments qrels;

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-id.train.txt");
    assertNotNull(qrels);
    assertEquals(4902, qrels.getQids().size());
    assertEquals(4902, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_ID_TRAIN);
    assertNotNull(qrels);
    assertEquals(4902, qrels.getQids().size());
    assertEquals(4902, getQrelsCount(qrels));

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-id.dev.txt");
    assertNotNull(qrels);
    assertEquals(1224, qrels.getQids().size());
    assertEquals(1224, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_ID_DEV);
    assertNotNull(qrels);
    assertEquals(1224, qrels.getQids().size());
    assertEquals(1224, getQrelsCount(qrels));

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-id.test.txt");
    assertNotNull(qrels);
    assertEquals(829, qrels.getQids().size());
    assertEquals(961, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_ID_TEST);
    assertNotNull(qrels);
    assertEquals(829, qrels.getQids().size());
    assertEquals(961, getQrelsCount(qrels));
  }

  @Test
  public void testMrTyDiJa() throws IOException{
    RelevanceJudgments qrels;

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-ja.train.txt");
    assertNotNull(qrels);
    assertEquals(3697, qrels.getQids().size());
    assertEquals(3697, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_JA_TRAIN);
    assertNotNull(qrels);
    assertEquals(3697, qrels.getQids().size());
    assertEquals(3697, getQrelsCount(qrels));

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-ja.dev.txt");
    assertNotNull(qrels);
    assertEquals(928, qrels.getQids().size());
    assertEquals(928, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_JA_DEV);
    assertNotNull(qrels);
    assertEquals(928, qrels.getQids().size());
    assertEquals(928, getQrelsCount(qrels));

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-ja.test.txt");
    assertNotNull(qrels);
    assertEquals(720, qrels.getQids().size());
    assertEquals(923, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_JA_TEST);
    assertNotNull(qrels);
    assertEquals(720, qrels.getQids().size());
    assertEquals(923, getQrelsCount(qrels));
  }

  @Test
  public void testMrTyDiKo() throws IOException{
    RelevanceJudgments qrels;

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-ko.train.txt");
    assertNotNull(qrels);
    assertEquals(1295, qrels.getQids().size());
    assertEquals(1317, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_KO_TRAIN);
    assertNotNull(qrels);
    assertEquals(1295, qrels.getQids().size());
    assertEquals(1317, getQrelsCount(qrels));

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-ko.dev.txt");
    assertNotNull(qrels);
    assertEquals(303, qrels.getQids().size());
    assertEquals(307, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_KO_DEV);
    assertNotNull(qrels);
    assertEquals(303, qrels.getQids().size());
    assertEquals(307, getQrelsCount(qrels));

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-ko.test.txt");
    assertNotNull(qrels);
    assertEquals(421, qrels.getQids().size());
    assertEquals(492, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_KO_TEST);
    assertNotNull(qrels);
    assertEquals(421, qrels.getQids().size());
    assertEquals(492, getQrelsCount(qrels));
  }

  @Test
  public void testMrTyDiRu() throws IOException{
    RelevanceJudgments qrels;

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-ru.train.txt");
    assertNotNull(qrels);
    assertEquals(5366, qrels.getQids().size());
    assertEquals(5366, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_RU_TRAIN);
    assertNotNull(qrels);
    assertEquals(5366, qrels.getQids().size());
    assertEquals(5366, getQrelsCount(qrels));

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-ru.dev.txt");
    assertNotNull(qrels);
    assertEquals(1375, qrels.getQids().size());
    assertEquals(1375, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_RU_DEV);
    assertNotNull(qrels);
    assertEquals(1375, qrels.getQids().size());
    assertEquals(1375, getQrelsCount(qrels));

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-ru.test.txt");
    assertNotNull(qrels);
    assertEquals(995, qrels.getQids().size());
    assertEquals(1168, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_RU_TEST);
    assertNotNull(qrels);
    assertEquals(995, qrels.getQids().size());
    assertEquals(1168, getQrelsCount(qrels));
  }

  @Test
  public void testMrTyDiSw() throws IOException{
    RelevanceJudgments qrels;

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-sw.train.txt");
    assertNotNull(qrels);
    assertEquals(2072, qrels.getQids().size());
    assertEquals(2401, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_SW_TRAIN);
    assertNotNull(qrels);
    assertEquals(2072, qrels.getQids().size());
    assertEquals(2401, getQrelsCount(qrels));

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-sw.dev.txt");
    assertNotNull(qrels);
    assertEquals(526, qrels.getQids().size());
    assertEquals(623, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_SW_DEV);
    assertNotNull(qrels);
    assertEquals(526, qrels.getQids().size());
    assertEquals(623, getQrelsCount(qrels));

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-sw.test.txt");
    assertNotNull(qrels);
    assertEquals(670, qrels.getQids().size());
    assertEquals(743, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_SW_TEST);
    assertNotNull(qrels);
    assertEquals(670, qrels.getQids().size());
    assertEquals(743, getQrelsCount(qrels));
  }

  @Test
  public void testMrTyDiTe() throws IOException{
    RelevanceJudgments qrels;

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-te.train.txt");
    assertNotNull(qrels);
    assertEquals(3880, qrels.getQids().size());
    assertEquals(3880, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_TE_TRAIN);
    assertNotNull(qrels);
    assertEquals(3880, qrels.getQids().size());
    assertEquals(3880, getQrelsCount(qrels));

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-te.dev.txt");
    assertNotNull(qrels);
    assertEquals(983, qrels.getQids().size());
    assertEquals(983, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_TE_DEV);
    assertNotNull(qrels);
    assertEquals(983, qrels.getQids().size());
    assertEquals(983, getQrelsCount(qrels));

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-te.test.txt");
    assertNotNull(qrels);
    assertEquals(646, qrels.getQids().size());
    assertEquals(677, getQrelsCount(qrels));
    // The value 677 differs from Mr. TyDi paper.
    // The paper reported 664, which is the qrel size before fixing the document slicing bug.
    // 677 should be the correct number.

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_TE_TEST);
    assertNotNull(qrels);
    assertEquals(646, qrels.getQids().size());
    assertEquals(677, getQrelsCount(qrels));
  }

  @Test
  public void testMrTyDiTh() throws IOException{
    RelevanceJudgments qrels;

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-th.train.txt");
    assertNotNull(qrels);
    assertEquals(3319, qrels.getQids().size());
    assertEquals(3360, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_TH_TRAIN);
    assertNotNull(qrels);
    assertEquals(3319, qrels.getQids().size());
    assertEquals(3360, getQrelsCount(qrels));

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-th.dev.txt");
    assertNotNull(qrels);
    assertEquals(807, qrels.getQids().size());
    assertEquals(817, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_TH_DEV);
    assertNotNull(qrels);
    assertEquals(807, qrels.getQids().size());
    assertEquals(817, getQrelsCount(qrels));

    qrels = new RelevanceJudgments("tools/topics-and-qrels/qrels.mrtydi-v1.1-th.test.txt");
    assertNotNull(qrels);
    assertEquals(1190, qrels.getQids().size());
    assertEquals(1368, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MRTYDI_V11_TH_TEST);
    assertNotNull(qrels);
    assertEquals(1190, qrels.getQids().size());
    assertEquals(1368, getQrelsCount(qrels));
  }

  @Test
  public void testBRIGHT() throws IOException{
    RelevanceJudgments qrels;

    qrels = RelevanceJudgments.fromQrels(Qrels.BRIGHT_BIOLOGY);
    assertNotNull(qrels);
    assertEquals(103, qrels.getQids().size());
    assertEquals(372, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BRIGHT_EARTH_SCIENCE);
    assertNotNull(qrels);
    assertEquals(116, qrels.getQids().size());
    assertEquals(585, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BRIGHT_ECONOMICS);
    assertNotNull(qrels);
    assertEquals(103, qrels.getQids().size());
    assertEquals(800, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BRIGHT_PSYCHOLOGY);
    assertNotNull(qrels);
    assertEquals(101, qrels.getQids().size());
    assertEquals(692, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BRIGHT_ROBOTICS);
    assertNotNull(qrels);
    assertEquals(101, qrels.getQids().size());
    assertEquals(520, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BRIGHT_STACKOVERFLOW);
    assertNotNull(qrels);
    assertEquals(117, qrels.getQids().size());
    assertEquals(478, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BRIGHT_SUSTAINABLE_LIVING);
    assertNotNull(qrels);
    assertEquals(108, qrels.getQids().size());
    assertEquals(576, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BRIGHT_PONY);
    assertNotNull(qrels);
    assertEquals(112, qrels.getQids().size());
    assertEquals(2219, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BRIGHT_LEETCODE);
    assertNotNull(qrels);
    assertEquals(142, qrels.getQids().size());
    assertEquals(262, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BRIGHT_AOPS);
    assertNotNull(qrels);
    assertEquals(111, qrels.getQids().size());
    assertEquals(524, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BRIGHT_THEOREMQA_THEOREMS);
    assertNotNull(qrels);
    assertEquals(76, qrels.getQids().size());
    assertEquals(151, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BRIGHT_THEOREMQA_QUESTIONS);
    assertNotNull(qrels);
    assertEquals(194, qrels.getQids().size());
    assertEquals(439, getQrelsCount(qrels));
  }

  @Test
  public void testBEIR() throws IOException{
    RelevanceJudgments qrels;

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_TREC_COVID_TEST);
    assertNotNull(qrels);
    assertEquals(50, qrels.getQids().size());
    assertEquals(66334, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_BIOASQ_TEST);
    assertNotNull(qrels);
    assertEquals(500, qrels.getQids().size());
    assertEquals(2359, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_NFCORPUS_TEST);
    assertNotNull(qrels);
    assertEquals(323, qrels.getQids().size());
    assertEquals(12334, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_NQ_TEST);
    assertNotNull(qrels);
    assertEquals(3452, qrels.getQids().size());
    assertEquals(4201, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_HOTPOTQA_TEST);
    assertNotNull(qrels);
    assertEquals(7405, qrels.getQids().size());
    assertEquals(14810, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_FIQA_TEST);
    assertNotNull(qrels);
    assertEquals(648, qrels.getQids().size());
    assertEquals(1706, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_SIGNAL1M_TEST);
    assertNotNull(qrels);
    assertEquals(97, qrels.getQids().size());
    assertEquals(1899, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_TREC_NEWS_TEST);
    assertNotNull(qrels);
    assertEquals(57, qrels.getQids().size());
    assertEquals(15655, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_ROBUST04_TEST);
    assertNotNull(qrels);
    assertEquals(249, qrels.getQids().size());
    assertEquals(311410, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_ARGUANA_TEST);
    assertNotNull(qrels);
    assertEquals(1406, qrels.getQids().size());
    assertEquals(1406, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_WEBIS_TOUCHE2020_TEST);
    assertNotNull(qrels);
    assertEquals(49, qrels.getQids().size());
    assertEquals(932, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_CQADUPSTACK_ANDROID_TEST);
    assertNotNull(qrels);
    assertEquals(699, qrels.getQids().size());
    assertEquals(1696, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_CQADUPSTACK_ENGLISH_TEST);
    assertNotNull(qrels);
    assertEquals(1570, qrels.getQids().size());
    assertEquals(3765, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_CQADUPSTACK_GAMING_TEST);
    assertNotNull(qrels);
    assertEquals(1595, qrels.getQids().size());
    assertEquals(2263, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_CQADUPSTACK_GIS_TEST);
    assertNotNull(qrels);
    assertEquals(885, qrels.getQids().size());
    assertEquals(1114, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_CQADUPSTACK_MATHEMATICA_TEST);
    assertNotNull(qrels);
    assertEquals(804, qrels.getQids().size());
    assertEquals(1358, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_CQADUPSTACK_PHYSICS_TEST);
    assertNotNull(qrels);
    assertEquals(1039, qrels.getQids().size());
    assertEquals(1933, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_CQADUPSTACK_PROGRAMMERS_TEST);
    assertNotNull(qrels);
    assertEquals(876, qrels.getQids().size());
    assertEquals(1675, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_CQADUPSTACK_STATS_TEST);
    assertNotNull(qrels);
    assertEquals(652, qrels.getQids().size());
    assertEquals(913, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_CQADUPSTACK_TEX_TEST);
    assertNotNull(qrels);
    assertEquals(2906, qrels.getQids().size());
    assertEquals(5154, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_CQADUPSTACK_UNIX_TEST);
    assertNotNull(qrels);
    assertEquals(1072, qrels.getQids().size());
    assertEquals(1693, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_CQADUPSTACK_WEBMASTERS_TEST);
    assertNotNull(qrels);
    assertEquals(506, qrels.getQids().size());
    assertEquals(1395, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_CQADUPSTACK_WORDPRESS_TEST);
    assertNotNull(qrels);
    assertEquals(541, qrels.getQids().size());
    assertEquals(744, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_QUORA_TEST);
    assertNotNull(qrels);
    assertEquals(10000, qrels.getQids().size());
    assertEquals(15675, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_DBPEDIA_ENTITY_TEST);
    assertNotNull(qrels);
    assertEquals(400, qrels.getQids().size());
    assertEquals(43515, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_SCIDOCS_TEST);
    assertNotNull(qrels);
    assertEquals(1000, qrels.getQids().size());
    assertEquals(29928, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_FEVER_TEST);
    assertNotNull(qrels);
    assertEquals(6666, qrels.getQids().size());
    assertEquals(7937, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_CLIMATE_FEVER_TEST);
    assertNotNull(qrels);
    assertEquals(1535, qrels.getQids().size());
    assertEquals(4681, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.BEIR_V1_0_0_SCIFACT_TEST);
    assertNotNull(qrels);
    assertEquals(300, qrels.getQids().size());
    assertEquals(339, getQrelsCount(qrels));
  }
  
  @Test
  public void testHC4() throws IOException{
    RelevanceJudgments qrels;
    
    qrels = RelevanceJudgments.fromQrels(Qrels.HC4_V1_0_RU_DEV);
    assertNotNull(qrels);
    assertEquals(4, qrels.getQids().size());
    assertEquals(265, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.HC4_V1_0_RU_TEST);
    assertNotNull(qrels);
    assertEquals(50, qrels.getQids().size());
    assertEquals(2970, getQrelsCount(qrels));
  
    qrels = RelevanceJudgments.fromQrels(Qrels.HC4_V1_0_FA_DEV);
    assertNotNull(qrels);
    assertEquals(10, qrels.getQids().size());
    assertEquals(565, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.HC4_V1_0_FA_TEST);
    assertNotNull(qrels);
    assertEquals(50, qrels.getQids().size());
    assertEquals(2522, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.HC4_V1_0_ZH_DEV);
    assertNotNull(qrels);
    assertEquals(10, qrels.getQids().size());
    assertEquals(466, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.HC4_V1_0_ZH_TEST);
    assertNotNull(qrels);
    assertEquals(50, qrels.getQids().size());
    assertEquals(2751, getQrelsCount(qrels));
  }

  @Test
  public void testNeuClir2022() throws IOException{
    RelevanceJudgments qrels;

    // % cut -f 1 -d ' ' tools/topics-and-qrels/qrels.neuclir22-fa.txt | uniq | wc
    //      46      46     146
    // % wc tools/topics-and-qrels/qrels.neuclir22-fa.txt
    //   34174  136696 1508848 tools/topics-and-qrels/qrels.neuclir22-fa.txt

    qrels = RelevanceJudgments.fromQrels(Qrels.NEUCLIR22_FA);
    assertNotNull(qrels);
    assertEquals(46, qrels.getQids().size());
    assertEquals(34174, getQrelsCount(qrels));

    // % cut -f 1 -d ' ' tools/topics-and-qrels/qrels.neuclir22-ru.txt | uniq | wc
    //      45      45     139
    // % wc tools/topics-and-qrels/qrels.neuclir22-ru.txt
    //   33006  132024 1455114 tools/topics-and-qrels/qrels.neuclir22-ru.txt

    qrels = RelevanceJudgments.fromQrels(Qrels.NEUCLIR22_RU);
    assertNotNull(qrels);
    assertEquals(45, qrels.getQids().size());
    assertEquals(33006, getQrelsCount(qrels));

    // % cut -f 1 -d ' ' tools/topics-and-qrels/qrels.neuclir22-zh.txt | uniq | wc
    //      49      49     155
    // % wc tools/topics-and-qrels/qrels.neuclir22-zh.txt
    //   36575  146300 1614196 tools/topics-and-qrels/qrels.neuclir22-zh.txt

    qrels = RelevanceJudgments.fromQrels(Qrels.NEUCLIR22_ZH);
    assertNotNull(qrels);
    assertEquals(49, qrels.getQids().size());
    assertEquals(36575, getQrelsCount(qrels));
  }

  @Test
  public void testMIRACL() throws IOException{
    RelevanceJudgments qrels;

    qrels = RelevanceJudgments.fromQrels(Qrels.MIRACL_V10_AR_DEV);
    assertNotNull(qrels);
    assertEquals(2896, qrels.getQids().size());
    assertEquals(29197, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MIRACL_V10_BN_DEV);
    assertNotNull(qrels);
    assertEquals(411, qrels.getQids().size());
    assertEquals(4206, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MIRACL_V10_EN_DEV);
    assertNotNull(qrels);
    assertEquals(799, qrels.getQids().size());
    assertEquals(8350, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MIRACL_V10_ES_DEV);
    assertNotNull(qrels);
    assertEquals(648, qrels.getQids().size());
    assertEquals(6443, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MIRACL_V10_FA_DEV);
    assertNotNull(qrels);
    assertEquals(632, qrels.getQids().size());
    assertEquals(6571, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MIRACL_V10_FI_DEV);
    assertNotNull(qrels);
    assertEquals(1271, qrels.getQids().size());
    assertEquals(12008, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MIRACL_V10_FR_DEV);
    assertNotNull(qrels);
    assertEquals(343, qrels.getQids().size());
    assertEquals(3429, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MIRACL_V10_HI_DEV);
    assertNotNull(qrels);
    assertEquals(350, qrels.getQids().size());
    assertEquals(3494, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MIRACL_V10_ID_DEV);
    assertNotNull(qrels);
    assertEquals(960, qrels.getQids().size());
    assertEquals(9668, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MIRACL_V10_JA_DEV);
    assertNotNull(qrels);
    assertEquals(860, qrels.getQids().size());
    assertEquals(8354, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MIRACL_V10_KO_DEV);
    assertNotNull(qrels);
    assertEquals(213, qrels.getQids().size());
    assertEquals(3057, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MIRACL_V10_RU_DEV);
    assertNotNull(qrels);
    assertEquals(1252, qrels.getQids().size());
    assertEquals(13100, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MIRACL_V10_SW_DEV);
    assertNotNull(qrels);
    assertEquals(482, qrels.getQids().size());
    assertEquals(5092, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MIRACL_V10_TE_DEV);
    assertNotNull(qrels);
    assertEquals(828, qrels.getQids().size());
    assertEquals(1606, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MIRACL_V10_TH_DEV);
    assertNotNull(qrels);
    assertEquals(733, qrels.getQids().size());
    assertEquals(7573, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.MIRACL_V10_ZH_DEV);
    assertNotNull(qrels);
    assertEquals(393, qrels.getQids().size());
    assertEquals(3928, getQrelsCount(qrels));
  }

  @Test
  public void testCIRAL() throws IOException{
    RelevanceJudgments qrels;

    qrels = RelevanceJudgments.fromQrels(Qrels.CIRAL_V10_HA_DEV);
    assertNotNull(qrels);
    assertEquals(10, qrels.getQids().size());
    assertEquals(165, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.CIRAL_V10_SO_DEV);
    assertNotNull(qrels);
    assertEquals(10, qrels.getQids().size());
    assertEquals(187, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.CIRAL_V10_SW_DEV);
    assertNotNull(qrels);
    assertEquals(10, qrels.getQids().size());
    assertEquals(196, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.CIRAL_V10_YO_DEV);
    assertNotNull(qrels);
    assertEquals(10, qrels.getQids().size());
    assertEquals(185, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.CIRAL_V10_HA_TEST_A);
    assertNotNull(qrels);
    assertEquals(80, qrels.getQids().size());
    assertEquals(1447, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.CIRAL_V10_SO_TEST_A);
    assertNotNull(qrels);
    assertEquals(99, qrels.getQids().size());
    assertEquals(1798, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.CIRAL_V10_SW_TEST_A);
    assertNotNull(qrels);
    assertEquals(85, qrels.getQids().size());
    assertEquals(1656, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.CIRAL_V10_YO_TEST_A);
    assertNotNull(qrels);
    assertEquals(100, qrels.getQids().size());
    assertEquals(1921, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.CIRAL_V10_HA_TEST_A_POOLS);
    assertNotNull(qrels);
    assertEquals(80, qrels.getQids().size());
    assertEquals(7288, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.CIRAL_V10_SO_TEST_A_POOLS);
    assertNotNull(qrels);
    assertEquals(99, qrels.getQids().size());
    assertEquals(9094, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.CIRAL_V10_SW_TEST_A_POOLS);
    assertNotNull(qrels);
    assertEquals(85, qrels.getQids().size());
    assertEquals(8079, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.CIRAL_V10_YO_TEST_A_POOLS);
    assertNotNull(qrels);
    assertEquals(100, qrels.getQids().size());
    assertEquals(8311, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.CIRAL_V10_HA_TEST_B);
    assertNotNull(qrels);
    assertEquals(312, qrels.getQids().size());
    assertEquals(5930, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.CIRAL_V10_SO_TEST_B);
    assertNotNull(qrels);
    assertEquals(239, qrels.getQids().size());
    assertEquals(4324, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.CIRAL_V10_SW_TEST_B);
    assertNotNull(qrels);
    assertEquals(114, qrels.getQids().size());
    assertEquals(2175, getQrelsCount(qrels));

    qrels = RelevanceJudgments.fromQrels(Qrels.CIRAL_V10_YO_TEST_B);
    assertNotNull(qrels);
    assertEquals(554, qrels.getQids().size());
    assertEquals(10569, getQrelsCount(qrels));
  }

  @Test
  public void testSymbolExpansion() throws IOException {
    Path expected;
    Path produced;

    expected = Path.of(System.getProperty("user.home"), ".cache", "pyserini", "topics-and-qrels", "qrels.msmarco-passage.dev-subset.txt");
    produced = RelevanceJudgments.getQrelsPath(Path.of("msmarco-passage.dev-subset"));
    assertNotNull(produced);
    assertEquals(expected, produced);

    expected = Path.of(System.getProperty("user.home"), ".cache", "pyserini", "topics-and-qrels", "qrels.msmarco-v2-passage.dev2.txt");
    produced = RelevanceJudgments.getQrelsPath(Path.of("msmarco-v2-passage.dev2"));
    assertNotNull(produced);
    assertEquals(expected, produced);

    expected = Path.of(System.getProperty("user.home"), ".cache", "pyserini", "topics-and-qrels", "qrels.miracl-v1.0-en-dev.tsv");
    produced = RelevanceJudgments.getQrelsPath(Path.of("miracl-v1.0-en-dev"));
    assertNotNull(produced);
    assertEquals(expected, produced);

    expected = Path.of(System.getProperty("user.home"), ".cache", "pyserini", "topics-and-qrels", "qrels.covid-round3.txt");
    produced = RelevanceJudgments.getQrelsPath(Path.of("covid-round3"));
    assertNotNull(produced);
    assertEquals(expected, produced);
    
    expected = Path.of(System.getProperty("user.home"), ".cache", "pyserini", "topics-and-qrels", "qrels.ciral-v1.0-yo-test-a-pools.tsv");
    produced = RelevanceJudgments.getQrelsPath(Path.of("ciral-v1.0-yo-test-a-pools"));
    assertNotNull(produced);
    assertEquals(expected, produced);

    expected = Path.of(System.getProperty("user.home"), ".cache", "pyserini", "topics-and-qrels", "qrels.adhoc.151-200.txt");
    produced = RelevanceJudgments.getQrelsPath(Path.of("adhoc.151-200"));
    assertNotNull(produced);
    assertEquals(expected, produced);

    expected = Path.of(System.getProperty("user.home"), ".cache", "pyserini", "topics-and-qrels", "qrels.microblog2012.txt");
    produced = RelevanceJudgments.getQrelsPath(Path.of("microblog2012"));
    assertNotNull(produced);
    assertEquals(expected, produced);

    expected = Path.of(System.getProperty("user.home"), ".cache", "pyserini", "topics-and-qrels", "qrels.terabyte04.701-750.txt");
    produced = RelevanceJudgments.getQrelsPath(Path.of("terabyte04.701-750"));
    assertNotNull(produced);
    assertEquals(expected, produced);

    // Test for non valid symbols
    expected = Path.of("thisdoesnotexist");
    produced = RelevanceJudgments.getQrelsPath(Path.of("thisdoesnotexist"));
    assertNotNull(produced);
    assertEquals(expected, produced);
  }
}
