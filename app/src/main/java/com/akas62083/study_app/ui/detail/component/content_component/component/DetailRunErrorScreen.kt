package com.akas62083.study_app.ui.detail.component.content_component.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.akas62083.study_app.domain.TextStruct
import com.akas62083.study_app.ui.detail.DetailUiState
import com.akas62083.study_app.ui.detail.RunScreenData
import com.akas62083.study_app.ui.detail.TabStatus

@Composable
fun DetailErrorScreen(
    tabStatus: TabStatus,
    modifier: Modifier = Modifier
) {
  //  val textStruct = (tabStatus as TabStatus.Run) as RunScreenData.Error 危険コード
  //  Column(modifier) {
  //      when(textStruct.code) {
  //          0 -> {
  //              Text("error: 原因不明のエラー。")
  //          }
  //          1 -> {
  //              Text("error: ${textStruct.line}行目の : の数が2じゃない。")
  //          }
  //          2 -> {
  //              Text("error: ' の数が奇数である。")
  //          }
  //          3 -> {
  //              Text("読み込み待ち")
  //          }
  //          4 -> {
  //              Text("error: 情報量が0")
  //          }
  //          else -> {
  //              Text("error: 未知のエラー。")
  //          }
  //      }
  //      Text("code: ${textStruct.code}")
  //  }
}
