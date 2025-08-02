package com.solux.dorandoran.presentation.mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.solux.dorandoran.core_ui.theme.Background01
import com.solux.dorandoran.core_ui.theme.Background02
import com.solux.dorandoran.core_ui.theme.Button02
import com.solux.dorandoran.core_ui.theme.Neutral60
import com.solux.dorandoran.core_ui.theme.baseBold
import com.solux.dorandoran.core_ui.theme.smallBold
import com.solux.dorandoran.core_ui.theme.smallRegular
import com.solux.dorandoran.presentation.mypage.navigation.MypageNavigator
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.solux.dorandoran.R


@Composable
fun MypageRoute(
    navigator: MypageNavigator
) {
    MypageScreen()
}

@Composable
fun MypageScreen(
    nickname: String = "송이" // 액세스 토큰으로 받아온 사용자 닉네임
) {
    val tabTitles = listOf("내 리뷰 보기", "내 감성글귀 보기")
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Button02)
    ) {
        // 상단 사용자 정보 영역
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "마이페이지",
                        style = baseBold,
                        color = Background01
                    )

                    // 우측 상단 설정 버튼 (임시)
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "설정",
                        modifier = Modifier
                            .wrapContentWidth(Alignment.End)
                            .size(24.dp),
                        tint = Background01
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    ProfileWithFloatingButton(
                        profileImageUrl = "",
                        onCameraClick = {}
                    )

                    Spacer(modifier = Modifier.width(15.dp))

                    Text(
                        text = nickname,
                        style = baseBold,
                        color = Background02
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Card(
                        shape = RoundedCornerShape(70.dp),
                        modifier = Modifier
                            .background(Background02)
                    ) {
                        Text(
                            text = "안녕하세요!",
                            style = smallBold,
                            color = Button02,
                            modifier = Modifier
                                .padding(horizontal = 15.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(Background02),
            elevation = CardDefaults.cardElevation(defaultElevation = 50.dp)
        ) {
            DiscussionTabHeader(
                tabTitles = tabTitles,
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { selectedTabIndex = it }
            )
            // 임시 탭
            when (selectedTabIndex) {
                0 -> {
                    Text("내 리뷰 목록", modifier = Modifier.padding(16.dp))
                }

                1 -> {
                    Text("내 감성글귀 목록", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}


@Composable
fun DiscussionTabHeader(
    tabTitles: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background01)
            .padding(top = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            tabTitles.forEachIndexed { index, title ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTabSelected(index) },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = title,
                            style = if (selectedTabIndex == index) smallBold else smallRegular,
                            color = Neutral60
                        )

                        Spacer(modifier = Modifier.height(6.dp))
                        Box(
                            modifier = Modifier
                                .height(2.dp)
                                .fillMaxWidth()
                                .background(
                                    if (selectedTabIndex == index) Neutral60 else Background02
                                )
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}


@Composable
fun ProfileWithFloatingButton(
    profileImageUrl: String,
    onCameraClick: () -> Unit
) {
    Box(
        modifier = Modifier.size(88.dp)
    ) {
        // 큰 원 - 프로필 이미지
        Box(
            modifier = Modifier
                .size(65.dp)
                .clip(CircleShape)
                .background(Background02)
                .align(Alignment.TopStart)
        ) {
            AsyncImage(
                model = profileImageUrl,
                contentDescription = "Profile",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // 작은 플로팅 버튼 - 카메라
        FloatingActionButton(
            onClick = onCameraClick,
            modifier = Modifier
                .size(28.dp)
                .align(Alignment.BottomEnd),
            containerColor = Background01,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 4.dp
            )
        ) {
            Icon(
                imageVector =  ImageVector.vectorResource(id = R.drawable.ic_profileimagechange),
                contentDescription = "Camera",
                modifier = Modifier.size(16.dp),
                tint = Button02
            )
        }
    }
}