package com.pbu.vgm_nursery.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RecordItem(
    SampleCode: String,
    JlhDaun: String,
    TinggiPokok: String,
    PanjangDaun: String,
    LebarDaun: String,
    DiameterBatang: String,
    Remarks: String,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable(onClick = onItemClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(0.6f)) {
            Text(text = SampleCode)
            Spacer(modifier = Modifier.height((8.dp)))
            Text(text = "PjgDaun : $PanjangDaun")
            Spacer(modifier = Modifier.height((8.dp)))
            Text(text = "Diameter : $DiameterBatang")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(0.6f)) {
            Text(text = "Tinggi : $TinggiPokok")
            Spacer(modifier = Modifier.height((8.dp)))
            Text(text = "LebarDauh : $LebarDaun")
            Spacer(modifier = Modifier.height((8.dp)))
            Text(text = "Remarks : $Remarks")
        }
        Text(
            text = JlhDaun,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}