package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Website
import com.example.ui.theme.BentoCharcoal
import com.example.ui.theme.BentoCoral
import com.example.ui.theme.BentoCoralText
import com.example.ui.theme.BentoLavender
import com.example.ui.theme.BentoLavenderText
import com.example.ui.theme.BentoOutline
import com.example.ui.theme.BentoSky
import com.example.ui.theme.BentoSkyText
import com.example.ui.theme.BentoSurfaceContainer
import com.example.ui.theme.BentoSurfaceVariant

@Composable
fun BentoShowcaseGrid(
    websites: List<Website>,
    onWebsiteClick: (Website) -> Unit,
    onViewAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Find representative items or fallback to top ranked
    val apple = websites.firstOrNull { it.id == "apple" } ?: websites.getOrNull(2) ?: return
    val google = websites.firstOrNull { it.id == "google" } ?: websites.getOrNull(0) ?: return
    val youtube = websites.firstOrNull { it.id == "youtube" } ?: websites.getOrNull(1) ?: return
    val xSite = websites.firstOrNull { it.id == "twitter" || it.id == "x" } ?: websites.getOrNull(6) ?: return
    val amazon = websites.firstOrNull { it.id == "amazon" } ?: websites.getOrNull(4) ?: return
    val netflix = websites.firstOrNull { it.id == "netflix" } ?: websites.getOrNull(8) ?: return

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        // Section Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp, start = 2.dp, end = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "POPULAR NOW",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "View all ${websites.size}",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable { onViewAllClick() }
                    .testTag("bento_view_all_link")
            )
        }

        // Bento Asymmetric Grid
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Row 1: Large Featured Card (Apple) + Right Column (Google + YouTube/X)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Large 2x2 Bento Tile: Apple (Lavender)
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(24.dp))
                        .clickable { onWebsiteClick(apple) }
                        .testTag("bento_tile_apple"),
                    shape = RoundedCornerShape(24.dp),
                    color = BentoLavender,
                    tonalElevation = 1.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .shadow(elevation = 2.dp, shape = RoundedCornerShape(14.dp))
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "A",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 20.sp,
                                    color = Color(0xFF1D1B20)
                                )
                            }

                            Surface(
                                shape = CircleShape,
                                color = Color.White.copy(alpha = 0.6f)
                            ) {
                                Text(
                                    text = "#${apple.rank}",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BentoLavenderText,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Column {
                            Text(
                                text = apple.name,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = BentoLavenderText
                            )
                            Text(
                                text = "Official Store & Ecosystem",
                                fontSize = 11.sp,
                                color = BentoLavenderText.copy(alpha = 0.75f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

                // Right Column: Wide Tile (Google) + Split Tile (YouTube + X)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Google Tile (Soft Sky Blue)
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clip(RoundedCornerShape(20.dp))
                        .clickable { onWebsiteClick(google) }
                        .testTag("bento_tile_google"),
                        shape = RoundedCornerShape(20.dp),
                        color = BentoSky
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .shadow(elevation = 1.dp, shape = RoundedCornerShape(10.dp))
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "G",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 17.sp,
                                    color = Color(0xFF1A73E8)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = google.name,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BentoSkyText
                                )
                                Text(
                                    text = "Search & Portal",
                                    fontSize = 10.sp,
                                    color = BentoSkyText.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }

                    // Split Tile (YouTube + X)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // YouTube (Soft Coral Red)
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize()
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { onWebsiteClick(youtube) }
                                .testTag("bento_tile_youtube"),
                            shape = RoundedCornerShape(20.dp),
                            color = BentoCoral
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "YT",
                                        fontWeight = FontWeight.Black,
                                        fontSize = 15.sp,
                                        color = Color(0xFFDC2626)
                                    )
                                    Text(
                                        text = "Videos",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = BentoCoralText.copy(alpha = 0.7f)
                                    )
                                }
                            }
                        }

                        // X / Twitter (Soft Purple)
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize()
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { onWebsiteClick(xSite) }
                                .testTag("bento_tile_x"),
                            shape = RoundedCornerShape(20.dp),
                            color = BentoSurfaceVariant
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "𝕏",
                                        fontWeight = FontWeight.Black,
                                        fontSize = 15.sp,
                                        color = Color(0xFF1D192B)
                                    )
                                    Text(
                                        text = "Social",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF49454F)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Row 2: Amazon (Bordered Pill Tile) + Netflix (Charcoal Dark Tile)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Amazon (Off-white / Bordered)
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(22.dp))
                        .clickable { onWebsiteClick(amazon) }
                        .testTag("bento_tile_amazon"),
                    shape = RoundedCornerShape(22.dp),
                    color = BentoSurfaceContainer,
                    border = BorderStroke(1.dp, BentoOutline.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFFFF9900)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "a",
                                    color = Color.White,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 16.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = amazon.name,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.OpenInNew,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                // Netflix (Charcoal Dark)
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(22.dp))
                        .clickable { onWebsiteClick(netflix) }
                        .testTag("bento_tile_netflix"),
                    shape = RoundedCornerShape(22.dp),
                    color = BentoCharcoal
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFE50914)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "N",
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = netflix.name,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Movies & TV",
                                fontSize = 10.sp,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }
        }
    }
}
