package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.model.WebsiteCategory
import com.example.ui.theme.BentoCharcoal
import com.example.ui.theme.BentoCoral
import com.example.ui.theme.BentoCoralText
import com.example.ui.theme.BentoLavender
import com.example.ui.theme.BentoLavenderText
import com.example.ui.theme.BentoMint
import com.example.ui.theme.BentoMintText
import com.example.ui.theme.BentoOutline
import com.example.ui.theme.BentoPeach
import com.example.ui.theme.BentoPeachText
import com.example.ui.theme.BentoSky
import com.example.ui.theme.BentoSkyText
import com.example.ui.theme.BentoSurfaceContainer
import com.example.ui.theme.BentoSurfaceVariant

/**
 * Returns a Bento background color & content color matching the website's category or rank
 */
fun getBentoTileColors(website: Website): Pair<Color, Color> {
    return when (website.category) {
        WebsiteCategory.TECH_HARDWARE, WebsiteCategory.DEVELOPER -> Pair(BentoLavender, BentoLavenderText)
        WebsiteCategory.SEARCH_AI -> Pair(BentoSky, BentoSkyText)
        WebsiteCategory.STREAMING, WebsiteCategory.SOCIAL -> Pair(BentoCoral, BentoCoralText)
        WebsiteCategory.FINANCE -> Pair(BentoMint, BentoMintText)
        WebsiteCategory.SHOPPING, WebsiteCategory.TRAVEL -> Pair(BentoPeach, BentoPeachText)
        WebsiteCategory.NEWS, WebsiteCategory.KNOWLEDGE -> Pair(BentoSurfaceVariant, Color(0xFF1D192B))
        WebsiteCategory.GAMING -> Pair(Color(0xFFFFE088), Color(0xFF241A00))
        WebsiteCategory.PRODUCTIVITY -> Pair(BentoSky, BentoSkyText)
        WebsiteCategory.ALL -> Pair(BentoSurfaceContainer, Color(0xFF1D1B20))
    }
}

/**
 * Bento Grid Tile (compact / 2-column format)
 */
@Composable
fun BentoGridTile(
    website: Website,
    isFavorite: Boolean,
    onWebsiteClick: (Website) -> Unit,
    onLaunchClick: (Website) -> Unit,
    onFavoriteToggle: (Website) -> Unit,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor) = getBentoTileColors(website)
    val brandColor = Color(website.brandColorHex)

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable { onWebsiteClick(website) }
            .testTag("bento_grid_tile_${website.id}"),
        shape = RoundedCornerShape(24.dp),
        color = bgColor,
        border = BorderStroke(1.dp, BentoOutline.copy(alpha = 0.25f)),
        tonalElevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header Row: Avatar + Favorite Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Elevated white badge with brand letter
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .shadow(elevation = 2.dp, shape = RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = website.name.take(1).uppercase(),
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp,
                        color = brandColor
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.6f)
                    ) {
                        Text(
                            text = "#${website.rank}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    IconButton(
                        onClick = { onFavoriteToggle(website) },
                        modifier = Modifier
                            .size(28.dp)
                            .testTag("bento_fav_${website.id}")
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (isFavorite) "Favorited" else "Favorite",
                            tint = if (isFavorite) Color(0xFFE11D48) else textColor.copy(alpha = 0.6f),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Website Title & Domain
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = website.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    if (website.isVerified) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Verified",
                            tint = textColor.copy(alpha = 0.8f),
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = website.domain,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = textColor.copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Action Pill
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onLaunchClick(website) },
                shape = RoundedCornerShape(12.dp),
                color = Color.White.copy(alpha = 0.8f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = website.category.title,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor.copy(alpha = 0.85f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.OpenInNew,
                        contentDescription = "Visit",
                        tint = textColor,
                        modifier = Modifier.size(13.dp)
                    )
                }
            }
        }
    }
}
